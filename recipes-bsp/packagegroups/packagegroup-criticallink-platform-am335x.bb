DESCRIPTION = "Extra tasks for TI AM335x platforms."
LICENSE = "MIT"
PR = "r0"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

CL_AM335X_BASE = "\
    bluetooth-scripts \
    bt-firmware \
    libdrm \
    libgles-omap3 \
    libgles-omap3-rawdemos \
    linux-firmware-wl12xx \
    populate-dev \
    ti-wifi-utils \
    udev-bluetooth \
    udev-extraconf \
"

RDEPENDS_${PN} = "\
    ${CL_AM335X_BASE} \
"
