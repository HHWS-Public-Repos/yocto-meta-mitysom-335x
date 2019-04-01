inherit kernel-uboot uboot-sign

python __anonymous () {
    kerneltypes = d.getVar('KERNEL_IMAGETYPES', True) or ""
    if 'fitImage' in kerneltypes.split():
        depends = d.getVar("DEPENDS", True)
        depends = "%s u-boot-mkimage-native dtc-native" % depends
        d.setVar("DEPENDS", depends)

        if d.getVar("UBOOT_ARCH", True) == "x86":
            replacementtype = "bzImage"
        else:
            replacementtype = "zImage"

	# Override KERNEL_IMAGETYPE_FOR_MAKE variable, which is internal
	# to kernel.bbclass . We have to override it, since we pack zImage
	# (at least for now) into the fitImage .
        typeformake = d.getVar("KERNEL_IMAGETYPE_FOR_MAKE", True) or ""
        if 'fitImage' in typeformake.split():
            d.setVar('KERNEL_IMAGETYPE_FOR_MAKE', typeformake.replace('fitImage', replacementtype))

        image = d.getVar('INITRAMFS_IMAGE', True)
        if image:
            d.appendVarFlag('do_assemble_fitimage_initramfs', 'depends', ' ${INITRAMFS_IMAGE}:do_image_complete')

        # Verified boot will sign the fitImage and append the public key to
        # U-boot dtb. We ensure the U-Boot dtb is deployed before assembling
        # the fitImage:
        if d.getVar('UBOOT_SIGN_ENABLE', True):
            uboot_pn = d.getVar('PREFERRED_PROVIDER_u-boot', True) or 'u-boot'
            d.appendVarFlag('do_assemble_fitimage', 'depends', ' %s:do_deploy' % uboot_pn)
}

# Options for the device tree compiler passed to mkimage '-D' feature:
UBOOT_MKIMAGE_DTCOPTS ??= ""

#
# Emit the fitImage ITS header
#
# $1 ... .its filename
fitimage_emit_fit_header() {
	cat << EOF >> ${1}
/dts-v1/;
/ {
	description = "U-Boot fitImage for ${DISTRO_NAME}/${PV}/${MACHINE}";
	#address-cells = <1>;
EOF
}

#
# Emit the fitImage section bits
#
# $1 ... .its filename
# $2 ... Section bit type: imagestart - image section start
#                          confstart  - configuration section start
#                          sectend    - section end
#                          fitend     - fitimage end
#
fitimage_emit_section_maint() {
	case $2 in
	imagestart)
	cat << EOF >> ${1}

	images {
EOF
	;;
	confstart)
	cat << EOF >> ${1}

	configurations {
EOF
	;;
	sectend)
	cat << EOF >> ${1}
	};
EOF
	;;
	fitend)
	cat << EOF >> ${1}
};
EOF
	;;
	esac
}

#
# Emit the fitImage ITS kernel section
#
# $1 ... .its filename
# $2 ... Image counter
# $3 ... Path to kernel image
# $4 ... Compression type
fitimage_emit_section_kernel() {

	kernel_csum="sha1"

	ENTRYPOINT=${UBOOT_ENTRYPOINT}
	if test -n "${UBOOT_ENTRYSYMBOL}"; then
	ENTRYPOINT=`${HOST_PREFIX}nm ${S}/vmlinux | \
		awk '$4=="${UBOOT_ENTRYSYMBOL}" {print $2}'`
	fi

	cat << EOF >> ${1}
		kernel-${2} {
			description = "Linux kernel";
			data = /incbin/("${3}");
			type = "kernel";
			arch = "${UBOOT_ARCH}";
			os = "linux";
			compression = "${4}";
			load = <${UBOOT_LOADADDRESS}>;
			entry = <${ENTRYPOINT}>;
			hash-1 {
				algo = "${kernel_csum}";
			};
		};
EOF
}

#
# Emit the fitImage ITS DTB section
#
# $1 ... .its filename
# $2 ... Image counter
# $3 ... Path to DTB image
fitimage_emit_section_dtb() {

	dtb_csum="sha1"

	cat << EOF >> ${1}
		fdt-${2} {
			description = "Flattened Device Tree blob";
			data = /incbin/("${3}");
			type = "flat_dt";
			arch = "${UBOOT_ARCH}";
			compression = "none";
			load = <0x87f00000>;
			hash-1 {
				algo = "${dtb_csum}";
			};
		};
EOF
}

#
# Emit the fitImage ITS DTBO section
#
# $1 ... .its filename
# $2 ... Image counter
# $3 ... Path to DTBO image
fitimage_emit_section_dtbo() {

	dtb_csum="sha1"

	load_addr="<0x87fc0000>"
	cat << EOF >> ${1}
		fdt-${2} {
			description = "Flattened Device Tree blob overlay";
			data = /incbin/("${3}");
			type = "flat_dt";
			arch = "${UBOOT_ARCH}";
			compression = "none";
			load = ${load_addr};
			hash-1 {
				algo = "${dtb_csum}";
			};
		};
EOF
}

#
# Emit the fitImage ITS setup section
#
# $1 ... .its filename
# $2 ... Image counter
# $3 ... Path to setup image
fitimage_emit_section_setup() {

	setup_csum="sha1"

	cat << EOF >> ${1}
		setup-${2} {
			description = "Linux setup.bin";
			data = /incbin/("${3}");
			type = "x86_setup";
			arch = "${UBOOT_ARCH}";
			os = "linux";
			compression = "none";
			load = <0x00090000>;
			entry = <0x00090000>;
			hash-1 {
				algo = "${setup_csum}";
			};
		};
EOF
}

#
# Emit the fitImage ITS ramdisk section
#
# $1 ... .its filename
# $2 ... Image counter
# $3 ... Path to ramdisk image
fitimage_emit_section_ramdisk() {

	ramdisk_csum="sha1"

	cat << EOF >> ${1}
		ramdisk-${2} {
			description = "ramdisk image";
			data = /incbin/("${3}");
			type = "ramdisk";
			arch = "${UBOOT_ARCH}";
			os = "linux";
			compression = "none";
			load = <${UBOOT_RD_LOADADDRESS}>;
			entry = <${UBOOT_RD_ENTRYPOINT}>;
			hash-1 {
				algo = "${ramdisk_csum}";
			};
		};
EOF
}

#
# Emit the fitImage ITS configuration section
#
# $1 	 ... .its filename
# $2 	 ... config ID
# $3 	 ... config description
# $4 	 ... Linux kernel ID
# $5 onwards ... DTB image ID's
fitimage_emit_section_config() {

	fdt_line=""
	conf_csum="sha1"

	if [ -n "${2}" ]; then
	conf_name=${2}
	fi

	if [ -n "${3}" ]; then
	conf_desc=\"${3}\"
	fi

	if [ -n "${4}" ]; then
	kernel_line=\"kernel-${4}\"
	fi

	# Loop through the device tree id's
	for i in ${*:5}
	do
	if [[ "${i}" -ne "${5}" ]]; then
		fdt_line+=", "
	fi
	fdt_line+="\"fdt-${i}\""
	done

	# Set configuration as default if name is 'conf'
	if [[ "${conf_name}" == 'conf' ]]; then
	cat << EOF >> ${1}
		default = "${conf_name}";
EOF
	fi

	# Write out configuration to file
	cat << EOF >> ${1}
		${conf_name} {
			description = ${conf_desc};
			kernel = ${kernel_line};
			fdt = ${fdt_line};
			hash-1 {
				algo = "${conf_csum}";
			};
		};
EOF
}

#
# Assemble fitImage
#
# $1 ... .its filename
# $2 ... fitImage name
# $3 ... include ramdisk
fitimage_assemble() {
	kernelcount=1
	dtbcount=""
	ramdiskcount=${3}
	setupcount=""
	rm -f ${1} arch/${ARCH}/boot/${2}

	fitimage_emit_fit_header ${1}

	#
	# Step 1: Prepare a kernel image section.
	#
	fitimage_emit_section_maint ${1} imagestart

	uboot_prep_kimage
	fitimage_emit_section_kernel ${1} "${kernelcount}" linux.bin "${linux_comp}"

	#
	# Step 2: Prepare a DTB image section
	#
	if test -n "${KERNEL_DEVICETREE}"; then
	dtbcount=1
	for DTB in ${KERNEL_DEVICETREE}; do
		if echo ${DTB} | grep -q '/dts/'; then
		bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
		DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
		fi
		DTB_PATH="arch/${ARCH}/boot/dts/${DTB}"
		if [ ! -e "${DTB_PATH}" ]; then
		DTB_PATH="arch/${ARCH}/boot/${DTB}"
		fi

		# Check if device tree blob is an overlay
		if echo ${DTB} | grep -q '.dtbo'; then
		fitimage_emit_section_dtbo ${1} ${dtbcount} ${DTB_PATH}
		else
		fitimage_emit_section_dtb ${1} ${dtbcount} ${DTB_PATH}
		fi

		dtbcount=`expr ${dtbcount} + 1`
	done

	# Check if the right number of device trees are given
	if [[ "${dtbcount}" -ne 6 ]]; then
		bberror "Invalid number of device trees: ${dtbcount}"
	fi
	fi

	#
	# Step 3: Prepare a setup section. (For x86)
	#
	if test -e arch/${ARCH}/boot/setup.bin ; then
	setupcount=1
	fitimage_emit_section_setup ${1} "${setupcount}" arch/${ARCH}/boot/setup.bin
	fi

	#
	# Step 4: Prepare a ramdisk section.
	#
	if [ "x${ramdiskcount}" = "x1" ] ; then
	copy_initramfs
	fitimage_emit_section_ramdisk ${1} "${ramdiskcount}" usr/${INITRAMFS_IMAGE}-${MACHINE}.cpio
	fi

	fitimage_emit_section_maint ${1} sectend

	# Force the first Kernel and DTB in the default config
	kernelcount=1
	if test -n "${dtbcount}"; then
	dtbcount=1
	fi

	#
	# Step 5: Prepare a configurations section
	#
	fitimage_emit_section_maint ${1} confstart

	fitimage_emit_section_config ${1} "conf" "Linux kernel, FDT devkit blob, No NAND, No NOR" 1 1
	fitimage_emit_section_config ${1} "conf_nand256" "Linux kernel, FDT maker blob, 256MB NAND, No NOR" 1 1 2
	fitimage_emit_section_config ${1} "conf_nand256_nor8" "Linux kernel, FDT maker blob, 256MB NAND, 8MB NOR" 1 1 3
	fitimage_emit_section_config ${1} "conf_nand512" "Linux kernel, FDT maker blob, 512MB NAND" 1 1 4
	fitimage_emit_section_config ${1} "conf_nand1024" "Linux kernel, FDT maker blob, 1GB NAND" 1 1 5

	fitimage_emit_section_maint ${1} sectend

	fitimage_emit_section_maint ${1} fitend

	#
	# Step 6: Assemble the image
	#
	uboot-mkimage \
	${@'-D "${UBOOT_MKIMAGE_DTCOPTS}"' if len('${UBOOT_MKIMAGE_DTCOPTS}') else ''} \
	-f ${1} \
	arch/${ARCH}/boot/${2}

	#
	# Step 7: Sign the image and add public key to U-Boot dtb
	#
	if [ "x${UBOOT_SIGN_ENABLE}" = "x1" ] ; then
	uboot-mkimage \
		${@'-D "${UBOOT_MKIMAGE_DTCOPTS}"' if len('${UBOOT_MKIMAGE_DTCOPTS}') else ''} \
		-F -k "${UBOOT_SIGN_KEYDIR}" \
		-K "${DEPLOY_DIR_IMAGE}/${UBOOT_DTB_BINARY}" \
		-r arch/${ARCH}/boot/${2}
	fi
}

do_assemble_fitimage() {
	if echo ${KERNEL_IMAGETYPES} | grep -wq "fitImage"; then
	cd ${B}
	fitimage_assemble fit-image.its fitImage
	fi
}

addtask assemble_fitimage before do_install after do_compile

do_assemble_fitimage_initramfs() {
	if echo ${KERNEL_IMAGETYPES} | grep -wq "fitImage" && \
	test -n "${INITRAMFS_IMAGE}" ; then
	cd ${B}
	fitimage_assemble fit-image-${INITRAMFS_IMAGE}.its fitImage-${INITRAMFS_IMAGE} 1
	fi
}

addtask assemble_fitimage_initramfs before do_deploy after do_install


kernel_do_deploy[vardepsexclude] = "DATETIME"
kernel_do_deploy_append() {
	# Update deploy directory
	if echo ${KERNEL_IMAGETYPES} | grep -wq "fitImage"; then
	cd ${B}
	echo "Copying fit-image.its source file..."
	its_base_name="fitImage-its-${PV}-${PR}-${MACHINE}-${DATETIME}"
	its_symlink_name=fitImage-its-${MACHINE}
	install -m 0644 fit-image.its ${DEPLOYDIR}/${its_base_name}.its
	linux_bin_base_name="fitImage-linux.bin-${PV}-${PR}-${MACHINE}-${DATETIME}"
	linux_bin_symlink_name=fitImage-linux.bin-${MACHINE}
	install -m 0644 linux.bin ${DEPLOYDIR}/${linux_bin_base_name}.bin

	if [ -n "${INITRAMFS_IMAGE}" ]; then
		echo "Copying fit-image-${INITRAMFS_IMAGE}.its source file..."
		its_initramfs_base_name="${KERNEL_IMAGETYPE}-its-${INITRAMFS_IMAGE}-${PV}-${PR}-${MACHINE}-${DATETIME}"
		its_initramfs_symlink_name=${KERNEL_IMAGETYPE}-its-${INITRAMFS_IMAGE}-${MACHINE}
		install -m 0644 fit-image-${INITRAMFS_IMAGE}.its ${DEPLOYDIR}/${its_initramfs_base_name}.its
		fit_initramfs_base_name="${KERNEL_IMAGETYPE}-${INITRAMFS_IMAGE}-${PV}-${PR}-${MACHINE}-${DATETIME}"
		fit_initramfs_symlink_name=${KERNEL_IMAGETYPE}-${INITRAMFS_IMAGE}-${MACHINE}
		install -m 0644 arch/${ARCH}/boot/fitImage-${INITRAMFS_IMAGE} ${DEPLOYDIR}/${fit_initramfs_base_name}.bin
	fi

	cd ${DEPLOYDIR}
	ln -sf ${its_base_name}.its ${its_symlink_name}.its
	ln -sf ${linux_bin_base_name}.bin ${linux_bin_symlink_name}.bin

	if [ -n "${INITRAMFS_IMAGE}" ]; then
		ln -sf ${its_initramfs_base_name}.its ${its_initramfs_symlink_name}.its
		ln -sf ${fit_initramfs_base_name}.bin ${fit_initramfs_symlink_name}.bin
	fi
	fi
}
