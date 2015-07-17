require ../../../meta/recipes-core/images/core-image-base.bb

IMAGE_INSTALL += \
    "alsa-lib \
     alsa-utils-amixer \
     alsa-utils-aplay \
     alsa-utils-speakertest \
     bash \
     bzip2 \
     canutils \
     curl \
     dhcp-client \
     dhcp-server \
     dropbear \
     ethtool \
     fbset \
     file \
     gadget-init-storage \
     gawk \
     gstreamer \
     hokey-pokey \
     hostap-daemon \
     i2c-tools \
     iperf \
     iptables \
     iw \
     kernel-modules \
     libdrm \
     libgcc \
     libgles-omap3 \
     libgles-omap3-rawdemos \
     linux-firmware-wl12xx \
     makedevs \
     memtester \
     memtool \
     mtd-utils \
     ntp \
     ntpdate \
     omap3-sgx-modules \
     opkg \
     pointercal-default \
     psplash \
     rs-485 \
     strace \
     tslib \
     tslib-calibrate \
     tslib-tests \
     vim \
     zip"

EXTRA_IMAGE_FEATURES = "debug-tweaks"

DESCRIPTION = ""
