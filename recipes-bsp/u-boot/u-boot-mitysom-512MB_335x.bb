require recipes-bsp/u-boot/u-boot-mitysom.inc

DESCRIPTION = "u-boot bootloader for ARM MPU devices"

COMPATIBLE_MACHINE = "mitysom_335x"

SPL_BINARY = "MLO"
UBOOT_SUFFIX = "img"
UBOOT_MACHINE = "mityarm335x_4kpage"
UBOOT_ENTRYPOINT = "0x80008000"
UBOOT_LOADADDRESS = "0x80008000"

PR = "r0"
PV_append = "512MB+git${SRCPV}"

SRC_URI = "git://support.criticallink.com/home/git/u-boot-mityarm-335x.git;protocol=git;branch=${BRANCH}"

# This version of u-boot is meant for 3.2 kernel which doesn't support device tree.
BRANCH = "u-boot-2013.10"

SRCREV = "${AUTOREV}"
