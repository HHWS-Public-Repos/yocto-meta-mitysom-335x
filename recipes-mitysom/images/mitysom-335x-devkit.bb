require mitysom.bb

IMAGE_INSTALL += "backlight \
     libicui18n \
     modpwr \
     qtbase \
     e2fsprogs-resize2fs \
"

KERNEL_DEVICETREE_append = "am335x-mitysom-maker.dts"

inherit populate_sdk_qt5

DESCRIPTION = ""
