require ../../../meta/recipes-core/images/core-image-base.bb

IMAGE_INSTALL = \
    "alsa-lib \
     alsa-utils \
     atk \
     audiofile \
     bash \
     binutils \
     boost \
     busybox \
     bzip2 \
     cairo \
     can-utils \
     ccache \
     cmake \
     compositeproto-dev \
     coreutils \
     cpio \
     curl \ 
     dbus \
     dhcp-client \
     dhcp-server \
     diffutils \
     directfb \
     dropbear \
     e2fsprogs \
     ethtool \
     expat \
     faad2 \
     fakeroot \
     fbset \
     file \
     findutils \
     fixesproto-dev \
     flac \
     fontconfig \
     freetype \
     gawk \
     gcc \
     gdb \
     gdk-pixbuf \
     giflib \
     glib-2.0 \
     gmp \
     gperf \
     gpgme \
     grep \
     gst-meta-base \
     gst-plugins-bad \
     gst-plugins-base \
     gst-plugins-good \
     gstreamer \
     gtk+ \
     gzip \
     hostap-conf \
     hostap-daemon \
     hostap-utils \
     i2c-tools \
     init-ifupdown \
     inputproto-dev \
     intltool \
     iperf \
     iptables \
     iw \
     kbproto-dev \
     kernel-modules \
     libassuan \
     libdrm \
     libffi \
     libgles-omap3 \
     libgles-omap3-rawdemos \
     libgpg-error \
     libice \
     libid3tag \
     liblockfile \
     libnl \
     libogg \
     libpng \
     libpthread-stubs \
     libsm \
     libsocketcan \
     libtheora \
     libvorbis \
     libx11 \
     libxau \
     libxcb \
     libxcomposite \
     libxdmcp \
     libxext \
     libxfixes \
     libxml2 \
     libxpm \
     libxrandr \
     libxrender \
     libxt \
     live555 \
     lmsensors-libsensors \
     lzo \ 
     makedevs \
     mingetty \
     mktemp \
     modutils-initscripts \
     mplayer-common \
     mtd-utils \
     ncurses \
     net-tools \
     omap3-sgx-modules \
     opkg \
     oprofile \
     orc \
     pango \
     pixman \
     pkgconfig \
     popt \
     procps \
     psplash \
     pth \
     qt4-embedded \
     randrproto-dev \
     readline \
     renderproto-dev \
     sed \
     shadow \
     strace \
     sysfsutils \
     sysklogd \
     sysvinit \
     tar \
     tslib \
     time \
     udev \
     unzip \
     usbutils \
     util-linux \
     v4l-utils \
     vim \
     watchdog \
     wget \
     which \
     wireless-tools \
     wpa-supplicant \
     xcb-proto-dev \
     xcb-util \
     xcb-util-image \
     xcb-util-keysyms \
     xcb-util-renderutil \
     xcb-util-wm \
     xextproto-dev \
     xproto-dev \
     xtrans-dev \
     zip \
     zlib"

EXTRA_IMAGE_FEATURES = "debug-tweaks"

DESCRIPTION = ""
