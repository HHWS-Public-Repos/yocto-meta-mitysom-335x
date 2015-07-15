DESCRIPTION = "tools to send and receive on rs-485"
SECTION = "dev tools"
LICENSE = "GPLv2"

PV = "1.0"
PR = "r0"

SRC_URI = "file://recv.c file://send.c file://Makefile file://serial.h"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

S = "${WORKDIR}"

do_install () {
   install -d ${D}${bindir}
   install -m 0755 recv_uart3 ${D}${bindir}
   install -m 0755 recv_uart4 ${D}${bindir}
   install -m 0755 send_uart3 ${D}${bindir}
   install -m 0755 send_uart4 ${D}${bindir}

}

do_compile() {
   oe_runmake

}

FILES_${PN} = "${bindir}/recv_uart3 \
               ${bindir}/recv_uart4 \
               ${bindir}/send_uart3 \
               ${bindir}/send_uart4"

INHIBIT_PACKAGE_DEBUG_SPLIT_${PN} = "1"
INSANE_SKIP_${PN} += "ldflags"
