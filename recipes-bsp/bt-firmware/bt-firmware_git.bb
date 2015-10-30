DESCRIPTION = "Firmware files for Bluetooth"
LICENSE = "TI-TSPA"
LIC_FILES_CHKSUM = "file://am335x/LICENCE;md5=1c9961176d6529283e0d0c983be41b45"

PACKAGE_ARCH = "${MACHINE_ARCH}"

#RCONFLICTS_${PN} = "linux-firmware-wl12xx"
#RREPLACES_${PN}  = "linux-firmware-wl12xx"

PV = "R8.5+git${SRCPV}"
PR = "r8"

COMPATIBLE_MACHINE = "ti33x|ti43x|dra7xx"

SRCREV = "46f6a1d0087bd9aac6e6cbae58ed9ee236e5f1b1"
BRANCH = "master"
SRC_URI = "git://git.ti.com/wilink8-bt/ti-bt-firmware.git;branch=${BRANCH} \
           file://0001-Makefile-allow-building-within-the-OE.patch \
           file://0001-bt-firmware-Remove-platform-check-and-install-defaul.patch"

PLATFORM = "unknown"
PLATFORM_ti33x = "am335x-evm"
PLATFORM_ti43x = "am437x-evm"

S = "${WORKDIR}/git"

do_compile() {
    :
}

do_install() {
    install -d ${D}${base_libdir}/firmware
    oe_runmake 'DEST_DIR=${D}' 'BASE_LIB_DIR=${base_libdir}' 'PLATFORM=${PLATFORM}' install
    # This firmware conflicts with linux-firmware-wl12xx. Skip it.
    rm -f ${D}${base_libdir}/firmware/TIInit_7.2.31.bts
}

FILES_${PN} += "${base_libdir}/firmware"
