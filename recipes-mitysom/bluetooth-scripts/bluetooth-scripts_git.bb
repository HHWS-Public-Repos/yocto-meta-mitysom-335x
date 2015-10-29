DESCRIPTION = "Scripts for TI bluetooth demo"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bb965abb1955d78452750ca40717999c"

PACKAGE_ARCH = "${MACHINE_ARCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = " \
    git://github.com/TI-ECS/wl1271-bluetooth.git;branch=${BRANCH} \
    file://BT_Init.sh \
"

SRCREV = "${AUTOREV}"
BRANCH = "master"

S = "${WORKDIR}/git"

do_compile() {
    :
}

do_install() {
    install -d ${D}${datadir}/wl1271-demos
    install -d ${D}${datadir}/wl1271-demos/bluetooth
    install -d ${D}${datadir}/wl1271-demos/bluetooth/scripts
    scripts=`find ${S}/script/common/ -type f -name "*.*"`
    for s in $scripts
    do
        install -m 0755 $s ${D}${datadir}/wl1271-demos/bluetooth/scripts/
    done
    cp -r ${S}/gallery ${D}${datadir}/wl1271-demos/bluetooth/
    install -m 0755 ${WORKDIR}/BT_Init.sh ${D}${datadir}/wl1271-demos/bluetooth/scripts/
}

FILES_${PN} += "${datadir}/wl1271-demos"
