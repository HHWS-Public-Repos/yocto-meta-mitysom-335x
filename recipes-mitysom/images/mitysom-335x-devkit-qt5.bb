require mitysom.bb

IMAGE_INSTALL_append = "\
        backlight \
        libicui18n \
        modpwr \
        evtest \
"

#QT5 support
DISTRO_FEATURES_remove = "x11 wayland"
IMAGE_INSTALL_append = "\
        packagegroup-qt5-toolchain-target \
        cinematicexperience \
        qtbase \
        qtbase-tools \
        qtbase-plugins \
"

inherit populate_sdk_qt5
include font-fixups.inc
DESCRIPTION = ""
