require recipes-kernel/linux/linux-mitysom335x_4.14.inc

PV = "${LINUX_VERSION}-devkit+git${SRCPV}"

KERNEL_DEFCONFIG = "mitysom-335x-devkit_defconfig"

KERNEL_DEVICETREE = "am335x-mitysom-devkit.dtb \
                     cl/am335x-mitysom-256MB.dtbo \
                     cl/am335x-mitysom-256MB-NOR.dtbo \
                     cl/am335x-mitysom-512MB.dtbo \
                     cl/am335x-mitysom-1GB.dtbo \
                     am335x-mitysom-maker.dtb"

KERNEL_IMAGE_SYMLINK_NAME = "${KERNEL_IMAGETYPE}_devkit"
