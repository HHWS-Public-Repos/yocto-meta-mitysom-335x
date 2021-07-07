FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append = " file://ts_skip_3_touches.patch;patchdir=${WORKDIR}"
