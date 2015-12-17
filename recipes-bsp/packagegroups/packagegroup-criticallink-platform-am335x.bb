DESCRIPTION = "Extra tasks for TI AM335x platforms."
LICENSE = "MIT"
PR = "r0"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

CL_AM335X_BASE = "\
    bluetooth-scripts \
    bluez4-agent \
    bt-firmware \
    gstreamer \
    hokey-pokey \
    libgles-omap3 \
    libgles-omap3-rawdemos \
    linux-firmware-wl12xx \
    pointercal-default \
    populate-dev \
    ti-wifi-utils \
    udev-bluetooth \
    udev-extraconf \
    usbutils \
    ${@base_contains('MACHINE_FEATURES', 'alsa', 'libasound-module-bluez', '',d)} \
"

RDEPENDS_${PN} = "\
    ${CL_AM335X_BASE} \
"
