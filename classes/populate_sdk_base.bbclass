inherit populate_sdk_cl

# Customizations for MitySOM 335x platform
do_populate_sdk[depends] +=  "sdk-files:do_deploy"

TARGET_ARCH="arm"

fakeroot create_sdk_files_platform() {
	# Install deploy directory
	for f in ${DEPLOY_DIR_IMAGE}/zImage-* ; do
		install -m 0755 $f ${SDK_OUTPUT}/${SDKPATH}/deploy
	done

	install -d ${SDK_OUTPUT}/${SDKPATH}/deploy/256MB_NAND
	install -m 0755 ${DEPLOY_DIR_IMAGE}/MLO ${SDK_OUTPUT}/${SDKPATH}/deploy/256MB_NAND/MLO
	install -m 0755 ${DEPLOY_DIR_IMAGE}/u-boot.img ${SDK_OUTPUT}/${SDKPATH}/deploy/256MB_NAND/u-boot.img

	install -d ${SDK_OUTPUT}/${SDKPATH}/deploy/512MB_NAND
	install -m 0755 ${DEPLOY_DIR_IMAGE}/../${MACHINE}-512MB/MLO ${SDK_OUTPUT}/${SDKPATH}/deploy/512MB_NAND/MLO
	install -m 0755 ${DEPLOY_DIR_IMAGE}/../${MACHINE}-512MB/u-boot.img ${SDK_OUTPUT}/${SDKPATH}/deploy/512MB_NAND/u-boot.img

	install -d ${SDK_OUTPUT}/${SDKPATH}/deploy/1GB_NAND
	install -m 0755 ${DEPLOY_DIR_IMAGE}/../${MACHINE}-1GB/MLO ${SDK_OUTPUT}/${SDKPATH}/deploy/1GB_NAND/MLO
	install -m 0755 ${DEPLOY_DIR_IMAGE}/../${MACHINE}-1GB/u-boot.img* ${SDK_OUTPUT}/${SDKPATH}/deploy/1GB_NAND/u-boot.img

	install -m 0755 ${DEPLOY_DIR_IMAGE}/fitImage-zImage_devkit.bin ${SDK_OUTPUT}/${SDKPATH}/deploy/fitImage
	
	install -d ${SDK_OUTPUT}/${SDKPATH}/sources
	install -m 0755 ${DEPLOY_DIR_IMAGE}/*335x.its ${SDK_OUTPUT}/${SDKPATH}/sources/fitImage.its

	install -d ${SDK_OUTPUT}/${SDKPATH}/deploy/USB_BOOT
	install -m 0755 ${DEPLOY_DIR_IMAGE}/../${MACHINE}-USBBOOT/u-boot-spl.bin ${SDK_OUTPUT}/${SDKPATH}/deploy/USB_BOOT/u-boot-spl.bin

	# Install additional files from SVN
	if [ -e ${DEPLOY_DIR_IMAGE}/mdk ]; then
		cd ${DEPLOY_DIR_IMAGE}/mdk
		for f in `find * -type d` ; do
			install -d ${SDK_OUTPUT}/${SDKPATH}/$f
		done
		for f in `find * -type f` ; do
			install -m 0755 $f ${SDK_OUTPUT}/${SDKPATH}/$f
		done
		cd -
	fi
}
