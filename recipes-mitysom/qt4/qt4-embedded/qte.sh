#!/bin/sh

if [ -e /dev/input/touchscreen0 ]
then
    QWS_MOUSE_PROTO="Tslib:/dev/input/touchscreen0 Auto"
    export QWS_MOUSE_PROTO
fi
