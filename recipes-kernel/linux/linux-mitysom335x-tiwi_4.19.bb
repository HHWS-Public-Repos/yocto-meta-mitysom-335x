require recipes-kernel/linux/linux-mitysom335x_4.19.inc

PV = "${LINUX_VERSION}-tiwi+git${SRCPV}"

KERNEL_DEFCONFIG = "mityarm-335x-tiwi-devkit_defconfig"

KERNEL_DEVICETREE = "am335x-mitysom-devkit.dtb \
                     cl/am335x-mitysom-256MB.dtbo \
                     cl/am335x-mitysom-256MB-NOR.dtbo \
                     cl/am335x-mitysom-512MB.dtbo \
                     cl/am335x-mitysom-1GB.dtbo "

KERNEL_IMAGE_LINK_NAME = "${KERNEL_IMAGETYPE}_tiwi"
