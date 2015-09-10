DESCRIPTION = "Default pointercal for touchscreen"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"
PR = "r0"

SRC_URI="file://pointercal"

S = "${WORKDIR}"

do_install(){
    install -d ${D}${sysconfdir}
    install -m 0644 pointercal ${D}${sysconfdir}
}

FILES_${PN} = "${sysconfdir}/pointercal"

INHIBIT_PACKAGE_DEBUG_SPLIT_${PN} = "1" 
INSANE_SKIP_${PN} += "ldflags" 
