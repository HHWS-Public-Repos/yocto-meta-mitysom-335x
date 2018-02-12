require recipes-bsp/u-boot/u-boot-mitysom.inc

DESCRIPTION = "u-boot bootloader for ARM MPU devices"

COMPATIBLE_MACHINE = "mitysom-335x"

SPL_BINARY = "MLO"
UBOOT_SUFFIX = "img"
UBOOT_MACHINE = "mitysom335x_devkit_256MB_defconfig"
UBOOT_ENTRYPOINT = "0x80008000"
UBOOT_LOADADDRESS = "0x80008000"

PR = "r0"
PV_append = "256MB+git${SRCPV}"

SRC_URI = "git://wanda/home/git/u-boot-mityarm-335x.git;protocol=git;branch=${BRANCH} \
"

BRANCH = "u-boot-2017.01_amk"

SRCREV = "${AUTOREV}"
