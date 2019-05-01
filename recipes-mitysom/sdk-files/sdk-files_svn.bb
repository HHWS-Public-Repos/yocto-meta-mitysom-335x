DESCRIPTION = "Extra files to include with MDK."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"
PR = "r1"

SRC_URI="svn://svnsrv/svn/mityomap/mityarm335x/trunk/sw;module=mdk;protocol=http"
SRCREV="${AUTOREV}"

inherit deploy

S = "${WORKDIR}"

do_deploy() {
    install -d ${DEPLOYDIR}
    cd ${S}
    for f in `find * -type d|grep -v deploy-sdk-files` ; do
        install -m 0755 -d ${DEPLOYDIR}/${f}
    done

    for f in `find * -type f|grep -v deploy-sdk-files` ; do
        install ${f} ${DEPLOYDIR}/${f}
    done
}

addtask deploy before do_build after do_compile
