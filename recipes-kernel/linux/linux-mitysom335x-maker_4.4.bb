require recipes-kernel/linux/linux-mitysom335x_4.4.inc

PV = "${LINUX_VERSION}-devkit+git${SRCPV}"

KERNEL_DEFCONFIG = "mityarm-335x-maker_defconfig"

KERNEL_DEVICETREE = "am335x-mitysom-maker.dts"

KERNEL_IMAGE_SYMLINK_NAME = "${KERNEL_IMAGETYPE}_devkit"
