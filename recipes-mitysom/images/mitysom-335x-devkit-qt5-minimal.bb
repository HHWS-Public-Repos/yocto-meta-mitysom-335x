SUMMARY = "MitySOM 335x Devkit Qt5 Minimal image."
LICENSE = "MIT"

SUMMARY = "Based on core-image-minimal.bb, with Qt5"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = " "

inherit core-image

IMAGE_ROOTFS_SIZE ?= "245760"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "" ,d)}"

#QT5 support
DISTRO_FEATURES_remove = "x11 wayland"

IMAGE_INSTALL_append = "\
	 kernel-modules \
	 ttf-dejavu-sans \
	 ttf-dejavu-serif \
	 openssh \
	 openssh-sftp-server \
	 dhcp-client \
         qtbase \
"

inherit populate_sdk_qt5
include font-fixups.inc
DESCRIPTION = ""
