require ${COREBASE}/meta/recipes-core/images/core-image-base.bb

IMAGE_INSTALL += "\
     packagegroup-criticallink-base \
     packagegroup-criticallink-console \
     packagegroup-criticallink-network \
     packagegroup-criticallink-platform-am335x \
     packagegroup-criticallink-util \
     gadget-init-storage \
     gstreamer \
     hokey-pokey \
     makedevs \
     opkg \
     pointercal-default \
     usbutils \
"

# blank root password
EXTRA_IMAGE_FEATURES = "debug-tweaks"

DESCRIPTION = ""
