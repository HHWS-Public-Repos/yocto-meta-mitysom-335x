require recipes-kernel/linux/linux-mitysom335x_3.2-devkit.bb

PV = "${LINUX_VERSION}-tiwi+git${SRCPV}"

KERNEL_DEFCONFIG = "mityarm-335x-tiwi-devkit_defconfig"
