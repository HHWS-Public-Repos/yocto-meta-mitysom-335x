DESCRIPTION = "Create sd-card images for the 335x"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"
PR = "r0"

SRC_URI = "file://sd-image-creator.sh"

S = "${WORKDIR}"

do_compile(){
	cd ${S}
	cp ${DEPLOY_DIR_IMAGE}/MLO .
	cp ${DEPLOY_DIR_IMAGE}/u-boot.img .
	cp ${DEPLOY_DIR}/images/mitysom-335x/fitImage .
	cp ${DEPLOY_DIR}/images/mitysom-335x/mitysom-335x-devkit-mitysom-335x.tar.bz2 .
	./sd-image-creator.sh \
	--preloader MLO \
	--uboot u-boot.img \
	--kernel fitImage \
	--bootfile ${DEPLOYDIR}/images/mitysom-335x/mdk/deploy/boot/uEnv.txt \
	--rootfs mitysom-335x-devkit-mitysom-335x.tar.bz2;
}

do_install() {
	install -m 0755 ${WORKDIR}/sd_card.img ${DEPLOY_DIR_IMAGE}/${MACHINE}.img
}
