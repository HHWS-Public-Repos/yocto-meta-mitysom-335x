DESCRIPTION = "USB OTG utility"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"
PR = "r0"

SRC_URI=" \
    file://97-bluetooth-hid2hci.rules \
    file://97-bluetooth.rules \
"

S = "${WORKDIR}"

do_install(){
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0755 97-bluetooth-hid2hci.rules ${D}${sysconfdir}/udev/rules.d
    install -m 0755 97-bluetooth.rules ${D}${sysconfdir}/udev/rules.d
}
