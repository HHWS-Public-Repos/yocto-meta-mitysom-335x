require recipes-bsp/u-boot/u-boot-mitysom.inc

DESCRIPTION = "u-boot bootloader for ARM MPU devices"

COMPATIBLE_MACHINE = "mitysom_335x"

PR = "r0"
PV_append = "+git${SRCPV}"

SRC_URI = "git://support.criticallink.com/home/git/u-boot-mityarm-335x.git;protocol=git;branch=${BRANCH}"

# This version of u-boot is meant for 3.2 kernel which doesn't support device tree.
BRANCH = "u-boot-2013.10"

SRCREV = "${AUTOREV}"
