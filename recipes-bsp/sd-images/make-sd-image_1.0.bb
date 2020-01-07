DESCRIPTION = "Create sd-card images for the 335x"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"
PR = "r0"

# Will use machine name or SOC_FAMILY to select make_sd_image.sh script from appropriate folder
SRC_URI = "file://make_sd_image.sh"

S = "${WORKDIR}"

# Script uses bash and bmaptool
DEPENDS_${PN} = "bash bmap-tools-native"

# Needs guestfish installed in host, really needs to be declared in local.conf
HOSTTOOLS += "guestfish"

BOOT_FILES_ti33x = ""
KERNEL_IMAGE_ti33x = "${DEPLOY_DIR}/images/mitysom-335x/fitImage"
ROOTFS_IMAGE_ti33x = "${DEPLOY_DIR}/images/mitysom-335x/mitysom-335x-devkit-mitysom-335x.tar.bz2"

# SOC_FAMILY is set to ti33x for all the mitysom-335x machines
do_compile_ti33x(){
	cd ${S}

	# Check if we need to add boot files
	if [ -n "${BOOT_FILES}" ]; then
		boot_args="--bootfile \"${BOOT_FILES}\" "
	fi

	# Copy to local workdir since guestfish can't follow symlinks
	cp ${DEPLOY_DIR_IMAGE}/MLO .
	cp ${DEPLOY_DIR_IMAGE}/u-boot.img .
	cp ${KERNEL_IMAGE} .
	cp ${ROOTFS_IMAGE} .
	./make_sd_image.sh \
	--gzip \
	--preloader MLO \
	--uboot u-boot.img \
	${boot_args} \
	--kernel "$(basename ${KERNEL_IMAGE})" \
	--rootfs "$(basename ${ROOTFS_IMAGE})";
}

inherit deploy

do_deploy() {
	install -m 0644 ${S}/sd_card.img.gz ${DEPLOY_DIR_IMAGE}/${MACHINE}.img.gz

	# Copy bmap file if it exists
	if [ -f "${S}/sd_card.img.bmap" ]
	then
		install -m 0644 ${S}/sd_card.img.bmap ${DEPLOY_DIR_IMAGE}/${MACHINE}.img.bmap
	fi
}
addtask deploy after do_compile

# Install script in native sdk
BBCLASSEXTEND = "native nativesdk"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/make_sd_image.sh ${D}${bindir}/make_sd_image.sh
}

FILES_${PN} = "${bindir}/make_sd_image.sh"
