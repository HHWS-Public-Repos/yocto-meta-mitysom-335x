inherit populate_sdk_cl

# Customizations for MitySOM 335x platform
SDK_DEPENDS += "sdk-files:do_deploy"

fakeroot create_sdk_files_platform() {
	# Install deploy directory
	for f in ${DEPLOY_DIR_IMAGE}/uImage_* ; do
		install -m 0755 $f ${SDK_OUTPUT}/${SDKPATH}/deploy
	done

	install -d ${SDK_OUTPUT}/${SDKPATH}/deploy/256MB_NAND
	install -m 0755 ${DEPLOY_DIR_IMAGE}/MLO-${MACHINE_ARCH}-1.0256MB* ${SDK_OUTPUT}/${SDKPATH}/deploy/256MB_NAND/MLO
	install -m 0755 ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE_ARCH}-1.0256MB* ${SDK_OUTPUT}/${SDKPATH}/deploy/256MB_NAND/u-boot.img

	install -d ${SDK_OUTPUT}/${SDKPATH}/deploy/512MB_NAND
	install -m 0755 ${DEPLOY_DIR_IMAGE}/MLO-${MACHINE_ARCH}-1.0512MB* ${SDK_OUTPUT}/${SDKPATH}/deploy/512MB_NAND/MLO
	install -m 0755 ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE_ARCH}-1.0512MB* ${SDK_OUTPUT}/${SDKPATH}/deploy/512MB_NAND/u-boot.img

	install -d ${SDK_OUTPUT}/${SDKPATH}/deploy/1GB_NAND
	install -m 0755 ${DEPLOY_DIR_IMAGE}/MLO-${MACHINE_ARCH}-1.01GB* ${SDK_OUTPUT}/${SDKPATH}/deploy/1GB_NAND/MLO
	install -m 0755 ${DEPLOY_DIR_IMAGE}/u-boot-${MACHINE_ARCH}-1.01GB* ${SDK_OUTPUT}/${SDKPATH}/deploy/1GB_NAND/u-boot.img

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
