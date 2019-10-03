#!/bin/sh

# Script to create an SD Card Image for the Mitysom 335x
# Created: 		 5/03/2019 -clloyd
# Last Modified: 5/09/2019 -clloyd

apppath=$(readlink -f "$0")
#appdir=$(dirname ${apppath})
app=$(basename "$apppath")

# function die
# print an error message and exit with status
# args the message to print
die()
{
	echo "$(hostname)": FATAL: "$*" >&2
	exit 1
}

usage()
{
	echo Usage:
	echo "$app [-s|--size <size>] [-o|--outfile <outfile>] [-p|--preloader <preloader bin>]"
	echo "     [-u|--uboot <u-Boot bin>] [-k|--kernel <file>] [-b|--bootfile <file>] [-a|--sparse]"
	echo "     [-f|--userfile <file>] [-v|--verbose] [-g|--gzip] [-r|--rootfs <rootfs.tar.gz>]"
	echo
	echo "    -s --size      size of image [default $SD_CARD_SIZE_MB MB]"
	echo "    -o --outfile   name of the output file [default is $IMAGE_FILE]"
	echo "    -p --preloader preloader to be used"
	echo "    -u --uboot     u-Boot image to be used"
	echo "    -k --kernel    kernel image to be used"
	echo "    -b --bootfile  files to add to the boot partition"
	echo "    -a --nosparse  disables the  creation of a sparse image file"
	echo "    -f --userfile  files to add to the user data partition"
	echo "    -v --verbose   sets verbose flag in shell"
	echo "    -g --gzip      will result in the output file being compressed with gzip"
	echo "    -r --rootfs    the filesystem tarball to use <rootfs.tar.gz>"
	echo "    -h --help      display this message"
}

SD_CARD_SIZE_MB=2048
BOOT_SIZE_MB=71
ROOTFS_SIZE_MB=1024
IMAGE_FILE=sd_card.img
PRELOADER=""
UBOOT_IMG=""
KERNEL=""
BOOT_FILES=""
USE_SPARSE="yes"
USERDATA_FILES=""
GZIP=N
ROOT_BALL=""

guestfish=$(which guestfish)
if [ -z "$guestfish" ]; then
	die guestfish missing.. run sudo apt-get install libguestfs-tools
fi
# Note that we use `"$@"' to let each command-line parameter expand to a
# separate word. The quotes around `$@' are essential!
# We need OPTIONS as the `eval set --' would nuke the return value of getopt.
OPTIONS=$(getopt -o s:o:p:u:k:b:f:r:gvah --long size:,outfile:,preloader:,uboot:,kernel:,bootfile:,userfile:,rootfs:,gzip,verbose,nosparse,help\
	  -n "$app" -- "$@")

[ $? != 0 ] && die "Terminating... getopt failed"
eval set -- "$OPTIONS"
while true ; do
	case "$1" in
		-s|--size) SD_CARD_SIZE_MB="$2"; shift 2;;
		-o|--outfile) IMAGE_FILE="$2"; shift 2;;
		-p|--preloader) PRELOADER="$2"; shift 2;;
		-u|--uboot) UBOOT_IMG="$2"; shift 2;;
		-k|--kernel) KERNEL="$2"; shift 2;;
		-b|--bootfile) BOOT_FILES="${BOOT_FILES} $2"; shift 2;;
		-a|--nosparse) USE_SPARSE="no"; shift ;;
		-f|--userfile) USERDATA_FILES="${USERDATA_FILES} $2"; shift 2;;
		-v|--verbose) set -v; shift ;;
		-g|--gzip) GZIP=Y; shift ;;
		-r|--rootfs) ROOT_BALL=$(readlink -f "$2"); shift 2;;
		-h|--help) usage ; exit 0; shift;;
		--) shift ; break ;;
		*) die "Internal error!: $*" ; shift;;
	esac
done

# Ensure the needed input files exist
[ -f "$PRELOADER" ] || die \""$PRELOADER"\" does not exist
[ -f "$UBOOT_IMG" ] || die \""$UBOOT_IMG"\" does not exist
[ -f "$KERNEL" ]    || die \""$KERNEL"\"    does not exist
[ -f "$ROOT_BALL" ] || die \""$ROOT_BALL"\" does not exist

# Remove an old output image file if it exists
rm -rf "$IMAGE_FILE"

if [ "$USE_SPARSE" = "yes" ]; then
	# generate image file using a sparse file
	truncate -s"${SD_CARD_SIZE_MB}"M "$IMAGE_FILE" || die unable to create SD image file
else
	# Create a blank image
	dd if=/dev/zero of="$IMAGE_FILE" bs=1M count="$SD_CARD_SIZE_MB" || die unable to create SD image file
fi

#######################################
###           PARTITIONING          ###
#######################################

# Layout:
# +---------------------+------------+-----------+------+
# | Description         | Partitions | Size      | Type |
# +---------------------+------------+-----------+------+
# | Boot Partition      | boot       | 70 MB     | FAT  |
# +---------------------+------------+-----------+------+
# | Rootfs Partition    | rootfs     | 1024 MB   | ext3 |
# +---------------------+------------+------------------+
# | User Data Partition | START_HERE | Remaining | ext3 |
# +---------------------+------------+-----------+------+

# Note: Sizes in sectors (1 sector = 512bytes)
# Note: MBR takes up first sector
boot_start=2048
boot_sectors=$((BOOT_SIZE_MB * 2 * 1024))
boot_end=$((boot_start + boot_sectors))
rootfs_start=$((boot_end + 1))
rootfs_sectors=$((ROOTFS_SIZE_MB * 2 * 1024))
rootfs_end=$((rootfs_start + rootfs_sectors))
userdata_start=$((rootfs_end + 1))
userdata_end=-1 # Note: -1 indicates end of image


# Catch guestfish errors
set -e

echo "Creating partitions"

# Create partitions
/usr/bin/guestfish --rw --format=raw -a "${IMAGE_FILE}" << EOF
	run
	part-init /dev/sda mbr
	part-add /dev/sda p ${boot_start} ${boot_end}
	part-set-mbr-id /dev/sda 1 0xc
	part-set-bootable /dev/sda 1 true
	mkfs vfat /dev/sda1 label:boot
	part-add /dev/sda p ${rootfs_start} ${rootfs_end}
	part-set-mbr-id /dev/sda 2 0x83
	mke2fs /dev/sda2 fstype:ext3 label:rootfs
	part-add /dev/sda p ${userdata_start} ${userdata_end}
	part-set-mbr-id /dev/sda 3 0x83
	mke2fs /dev/sda3 fstype:ext3 label:START_HERE
EOF

#############################################
###       Populate Rootfs Partition       ###
#############################################
echo "=> Populate Rootfs Partition"
# Copy in rootfs tarball to /dev/sda2
/usr/bin/guestfish -a "${IMAGE_FILE}" << EOF
	run
	mount /dev/sda2 /
	tar-in  ${ROOT_BALL} / compress:bzip2
	chown 0 0 /
	umount /
	sync
EOF

#############################################
###        Populate Boot Partition        ###
#############################################
echo "=> Populate Boot Partition"
# Copy in MLO, uboot, and the kernel
/usr/bin/guestfish -a "${IMAGE_FILE}" << EOF
	run
	mount /dev/sda2 /
	mount /dev/sda1 /mnt
	copy-in ${PRELOADER} /mnt
	copy-in ${UBOOT_IMG} /mnt
	copy-in ${KERNEL} /mnt
	umount /mnt
	umount /
	sync
EOF

# Copy in boot files if they exist
if [ "${BOOT_FILES}" != "" ]; then
/usr/bin/guestfish -a "${IMAGE_FILE}" << EOF
	run
	mount /dev/sda2 /
	mount /dev/sda1 /mnt
	copy-in ${BOOT_FILES} /mnt
	umount /mnt
	umount /
	sync
EOF
fi

#############################################
###      Populate User Data Partition     ###
#############################################
if [ "${USERDATA_FILES}" != "" ]; then
echo "=> Populate User Data Partition"

# Copy in User Data files if they exist
/usr/bin/guestfish -a "${IMAGE_FILE}" << EOF
	run
	mount /dev/sda2 /
	mount /dev/sda3 /mnt
	copy-in ${USERDATA_FILES} /mnt
	umount /mnt
	umount /
	sync
EOF
fi

#############################################
###        Generate bmap file             ###
#############################################
bmaptool=$(which bmaptool)
if [ -n "$bmaptool" ]; then
	echo "Generating .bmap file"
	"$bmaptool" create "$IMAGE_FILE" > "${IMAGE_FILE}.bmap"
fi

#############################################
###           COMPRESSING IMAGE           ###
#############################################
if [ "Y" = "$GZIP" ] ; then
	echo compressing "${IMAGE_FILE}"
	gzip -f "${IMAGE_FILE}"
fi

exit 0
