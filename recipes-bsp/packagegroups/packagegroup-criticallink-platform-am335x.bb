DESCRIPTION = "Extra tasks for TI AM335x platforms."
LICENSE = "MIT"
PR = "r0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

CL_AM335X_BASE = "\
    bluetooth-scripts \
    bt-firmware \
    gstreamer1.0 \
    hokey-pokey \
    ti-sgx-ddk-um \
    linux-firmware-wl12xx \
    pointercal-default \
    rsync \
    udev-bluetooth \
    udev-extraconf \
    usbutils \
    ${@bb.utils.contains('MACHINE_FEATURES', 'alsa', 'libasound-module-bluez', '',d)} \
"

RDEPENDS_${PN} = "\
    ${CL_AM335X_BASE} \
"
