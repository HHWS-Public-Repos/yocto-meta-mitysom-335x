DESCRIPTION = "Stresstest scripts. Should be distributed internally as ipk."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_configure[noexec] = "1"

PV = "1.0"
PR = "r0"

# Unified CL stresstest for all platforms is default.
STRESSTEST_MODULE ?= "unified"

SRC_URI = "svn://wanda.syr.criticallink.com/svn/criticallink/test/stresstest;module=${STRESSTEST_MODULE};protocol=http"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/${STRESSTEST_MODULE}"

do_install () {
	install -m 0755 -d ${D}${prefix}/local/stresstest/platform/335x
	cd "${S}"
	for f in `find platform/335x -type d`; do
		install -m 0755 -d ${D}${prefix}/local/stresstest/${f}/
	done
	for f in `find platform/335x -type f`; do
		install -m 0755 $f ${D}${prefix}/local/stresstest/${f}
	done
}

RDEPENDS_${PN} = "stresstest"
FILES_${PN} = "${prefix}/local/stresstest/platform/335x"

# Don't strip the stresstest binaries.
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
