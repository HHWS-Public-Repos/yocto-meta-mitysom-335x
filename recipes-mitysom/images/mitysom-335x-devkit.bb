require mitysom.bb

IMAGE_INSTALL += "backlight \
     libicui18n \
     modpwr \ 
     qtbase \
"

KERNEL_DEVICETREE_append = "am335x-mitysom-maker.dts"

inherit populate_sdk_qt5

DESCRIPTION = ""
