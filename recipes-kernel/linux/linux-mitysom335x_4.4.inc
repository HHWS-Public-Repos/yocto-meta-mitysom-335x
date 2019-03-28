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

inherit kernel kernel-fitimage
require recipes-kernel/linux/linux-yocto.inc

DEPENDS += " amx3-cm3"
RRECOMMENDS_${PN} += " kernel-modules"

SRC_URI = "git://wanda/home/git/linux-mityarm-335x.git;protocol=git;bareclone=1;branch=${KBRANCH}"

FILESEXTRAPATHS =. "${FILE_DIRNAME}/linux-mitysom335x:"

SRC_URI += "file://defconfig \
            file://mitysom-335x.scc \
            file://mitysom-335x.cfg \
            file://mitysom-335x-user-config.cfg \
            file://mitysom-335x-user-patches.scc \
           "

KBRANCH = "linux4.4_rc"
KDIR = "${WORKDIR}/git"

LINUX_VERSION ?= "4.4.32"
LINUX_VERSION_EXTENSION ?= "4.4.32"

SRCREV="${AUTOREV}"

PR = "r1"
PV ?= "${LINUX_VERSION}-devkit+git${SRCPV}"

COMPATIBLE_MACHINE_mitysom-335x = "mitysom-335x"

KERNEL_DEFCONFIG ?= "mitysom-335x-devkit_defconfig"
KERNEL_IMAGE_SYMLINK_NAME ?= "${KERNEL_IMAGETYPE}_devkit"

do_configure() {
	unset LDFLAGS
        oe_runmake ${KERNEL_DEFCONFIG}
}

# Copy the amx3-cm3 firmware if it is available
do_compile_prepend() {
	if [ -e "${STAGING_DIR_HOST}/${base_libdir}/firmware/am335x-pm-firmware.bin" ]
	then
		cp "${STAGING_DIR_HOST}/${base_libdir}/firmware/am335x-pm-firmware.bin" "${S}/firmware"
	fi
}
