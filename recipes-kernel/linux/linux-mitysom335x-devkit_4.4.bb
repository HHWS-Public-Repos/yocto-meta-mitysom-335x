require recipes-kernel/linux/linux-mitysom335x_4.4.inc

PV = "${LINUX_VERSION}-devkit+git${SRCPV}"

KERNEL_DEFCONFIG = "mitysom-335x-devkit_defconfig"

KERNEL_DEVICETREE = "am335x-mitysom.dtb"
KERNEL_DEVICETREE += "am335x-mitysom-NOR.dtb"
KERNEL_DEVICETREE += "am335x-mitysom-256M.dtb"
KERNEL_DEVICETREE += "am335x-mitysom-256M-NOR.dtb"
KERNEL_DEVICETREE += "am335x-mitysom-512M.dtb"
KERNEL_DEVICETREE += "am335x-mitysom-512M-NOR.dtb"
KERNEL_DEVICETREE += "am335x-mitysom-1G.dtb"
KERNEL_DEVICETREE += "am335x-mitysom-1G-NOR.dtb"


KERNEL_IMAGE_SYMLINK_NAME = "${KERNEL_IMAGETYPE}_devkit"
