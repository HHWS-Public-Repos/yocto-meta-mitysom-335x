require recipes-kernel/linux/linux-mitysom335x_4.4.inc

PV = "${LINUX_VERSION}-tiwi+git${SRCPV}"

KERNEL_DEFCONFIG = "mityarm-335x-tiwi-devkit_defconfig"

KERNEL_DEVICETREE = "am335x-mitysom-devkit.dts"

KERNEL_IMAGE_SYMLINK_NAME = "${KERNEL_IMAGETYPE}_tiwi"
