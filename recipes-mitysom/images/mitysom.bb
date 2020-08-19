SUMMARY = "MitySOM 335x Devkit image."
LICENSE = "MIT"

IMAGE_FEATURES += "\
    debug-tweaks \
    splash \
    package-management \
    ssh-server-openssh \
"

IMAGE_INSTALL += "\
    packagegroup-criticallink-base \
    packagegroup-criticallink-console \
    packagegroup-criticallink-network \
    packagegroup-criticallink-platform-am335x \
    packagegroup-criticallink-util \
    gadget-init-storage \
    makedevs \
    fbfill \
    gdbserver \
"

inherit core-image
