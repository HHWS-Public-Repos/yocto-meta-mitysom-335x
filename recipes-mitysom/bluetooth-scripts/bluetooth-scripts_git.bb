DESCRIPTION = "Scripts for TI bluetooth demo"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bb965abb1955d78452750ca40717999c"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/TI-ECS/wl1271-bluetooth.git;branch=${BRANCH}"

SRCREV = "${AUTOREV}"
BRANCH = "master"

S = "${WORKDIR}/git"

do_compile() {
    :
}

do_install() {
    install -d ${D}${datadir}/wl1271-demos
    install -d ${D}${datadir}/wl1271-demos/bluetooth
    cp -r ${S}/script ${D}${datadir}/wl1271-demos/bluetooth/
    cp -r ${S}/gallery ${D}${datadir}/wl1271-demos/bluetooth/
}

FILES_${PN} += "${datadir}/wl1271-demos"
