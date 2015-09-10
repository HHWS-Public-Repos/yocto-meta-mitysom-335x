FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += \
     "file://psplash-bar-img.h \
      file://psplash-colors.h"

do_configure_append() {
     cp ${WORKDIR}/psplash-colors.h ${S}/
     cp ${WORKDIR}/psplash-bar-img.h ${S}/
}
