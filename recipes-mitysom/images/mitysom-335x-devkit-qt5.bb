require mitysom.bb

IMAGE_INSTALL += "backlight \
     libicui18n \
     modpwr \
"

#QT5 support
DISTRO_FEATURES_remove = "x11 wayland"
IMAGE_INSTALL += "packagegroup-qt5-toolchain-target \
        cinematicexperience \
        qtbase \
        qtbase-tools \
        qtbase-plugins \
"

inherit populate_sdk_qt5
include font-fixups.inc
DESCRIPTION = ""
