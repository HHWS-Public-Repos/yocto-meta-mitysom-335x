require mitysom.bb

IMAGE_INSTALL += "backlight \
     libicui18n \
     modpwr \ 
     qtbase \
"

inherit populate_sdk_qt5

DESCRIPTION = ""
