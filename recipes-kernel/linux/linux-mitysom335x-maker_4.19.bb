require recipes-kernel/linux/linux-mitysom335x_4.19.inc

PV = "${LINUX_VERSION}-devkit+git${SRCPV}"

KERNEL_DEFCONFIG = "mitysom-335x-devkit_defconfig"

KERNEL_DEVICETREE = "am335x-mitysom-maker.dtb"

KERNEL_IMAGE_LINK_NAME = "${KERNEL_IMAGETYPE}_maker"
