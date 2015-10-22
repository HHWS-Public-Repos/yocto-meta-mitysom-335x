require recipes-kernel/linux/linux-mitysom335x_3.2.inc

PV = "${LINUX_VERSION}-devkit+git${SRCPV}"

KERNEL_DEFCONFIG = "mityarm-335x-devkit_defconfig"
