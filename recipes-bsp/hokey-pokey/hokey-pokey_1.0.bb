DESCRIPTION = "USB OTG utility"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"
PR = "r0"

SRC_URI="file://99-hokey-pokey.rules file://hokey-pokey.sh"

S = "${WORKDIR}"

do_install(){
    install -d ${D}${sysconfdir}/udev/rules.d
    install -d ${D}${bindir}
    install -m 0755 99-hokey-pokey.rules ${D}${sysconfdir}/udev/rules.d
    install -m 0755 hokey-pokey.sh ${D}${bindir}
}

FILES_${PN} = "${bindir}/hokey-pokey.sh \
               ${sysconfdir}/udev/rules.d/99-hokey-pokey.rules"

INHIBIT_PACKAGE_DEBUG_SPLIT_${PN} = "1" 
INSANE_SKIP_${PN} += "ldflags" 
