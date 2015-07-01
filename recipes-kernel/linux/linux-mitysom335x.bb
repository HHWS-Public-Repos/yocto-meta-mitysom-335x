# This file was derived from the linux-yocto-custom.bb recipe in
# oe-core.
#
# linux-yocto-custom.bb:
#
#   A yocto-bsp-generated kernel recipe that uses the linux-yocto and
#   oe-core kernel classes to apply a subset of yocto kernel
#   management to git managed kernel repositories.
#
# Warning:
#
#   Building this kernel without providing a defconfig or BSP
#   configuration will result in build or boot errors. This is not a
#   bug.
#
# Notes:
#
#   patches: patches can be merged into to the source git tree itself,
#            added via the SRC_URI, or controlled via a BSP
#            configuration.
#
#   example configuration addition:
#            SRC_URI += "file://smp.cfg"
#   example patch addition:
#            SRC_URI += "file://0001-linux-version-tweak.patch
#   example feature addition:
#            SRC_URI += "file://feature.scc"
#

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Add a run-time dependency for the PM firmware to be installed
# on the target file system.
DEPENDS = " am33x-cm3"

SRC_URI = "git://support.criticallink.com/home/git/linux-mityarm-335x.git;protocol=git;bareclone=1;branch=${KBRANCH}"

SRC_URI += "file://mitysom_335x.scc \
            file://mitysom_335x.cfg \
            file://mitysom_335x-user-config.cfg \
            file://mitysom_335x-user-patches.scc \
           "

KBRANCH = "mityarm-linux-v3.2"
KDIR = "${WORKDIR}/git"

LINUX_VERSION ?= "3.2"
LINUX_VERSION_EXTENSION ?= "3.2"

SRCREV="${AUTOREV}"

PR = "r0"
PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE_mitysom_335x = "mitysom_335x"

do_configure() {
        oe_runmake mityarm-335x-devkit_defconfig
}

# Copy the am33x-cm3 firmware if it is available
do_compile_prepend() {
	if [ -e "${STAGING_DIR_HOST}/${base_libdir}/firmware/am335x-pm-firmware.bin" ]
	then
		cp "${STAGING_DIR_HOST}/${base_libdir}/firmware/am335x-pm-firmware.bin" "${S}/firmware"
	fi
}
