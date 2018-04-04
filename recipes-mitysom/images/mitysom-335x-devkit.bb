require mitysom.bb

DISTRO_FEATURES_remove = "x11 wayland"
IMAGE_INSTALL += "backlight \
     libicui18n \
     modpwr \ 
     qtbase \
"

inherit populate_sdk_qt5

DESCRIPTION = ""
