DESCRIPTION = "Populate /dev with character and block devices"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"
PR = "r1"

S = "${WORKDIR}"

fakeroot do_install(){
    install -d ${D}${base_prefix}/dev
    install -d ${D}${base_prefix}/dev/input
    mknod -m 640 ${D}${base_prefix}/dev/apm_bios c 10 134
    mknod -m 640 ${D}${base_prefix}/dev/console c 5 1
    mknod -m 600 ${D}${base_prefix}/dev/fb0 c 29 0
    mknod -m 640 ${D}${base_prefix}/dev/hda b 3 0
    mknod -m 640 ${D}${base_prefix}/dev/hda1 b 3 1
    mknod -m 640 ${D}${base_prefix}/dev/hda2 b 3 2
    mknod -m 640 ${D}${base_prefix}/dev/hda3 b 3 3
    mknod -m 640 ${D}${base_prefix}/dev/hda4 b 3 4
    mknod -m 640 ${D}${base_prefix}/dev/hda5 b 3 5
    mknod -m 640 ${D}${base_prefix}/dev/hda6 b 3 6
    mknod -m 640 ${D}${base_prefix}/dev/hda7 b 3 7
    mknod -m 640 ${D}${base_prefix}/dev/hda8 b 3 8
    mknod -m 640 ${D}${base_prefix}/dev/hda9 b 3 9
    mknod -m 640 ${D}${base_prefix}/dev/hda10 b 3 10
    mknod -m 640 ${D}${base_prefix}/dev/hda11 b 3 11
    mknod -m 640 ${D}${base_prefix}/dev/hda12 b 3 12
    mknod -m 640 ${D}${base_prefix}/dev/hda13 b 3 13
    mknod -m 640 ${D}${base_prefix}/dev/hda14 b 3 14
    mknod -m 640 ${D}${base_prefix}/dev/hda15 b 3 15
    mknod -m 640 ${D}${base_prefix}/dev/hda16 b 3 16
    mknod -m 640 ${D}${base_prefix}/dev/hda17 b 3 17
    mknod -m 640 ${D}${base_prefix}/dev/hda18 b 3 18
    mknod -m 640 ${D}${base_prefix}/dev/hda19 b 3 19
    mknod -m 600 ${D}${base_prefix}/dev/initctl p
    mknod -m 640 ${D}${base_prefix}/dev/input/event0 c 13 64
    mknod -m 640 ${D}${base_prefix}/dev/input/event1 c 13 65
    mknod -m 640 ${D}${base_prefix}/dev/input/event2 c 13 66
    mknod -m 640 ${D}${base_prefix}/dev/input/event3 c 13 67
    mknod -m 640 ${D}${base_prefix}/dev/input/event4 c 13 68
    mknod -m 640 ${D}${base_prefix}/dev/input/event5 c 13 69
    mknod -m 640 ${D}${base_prefix}/dev/input/event6 c 13 70
    mknod -m 640 ${D}${base_prefix}/dev/input/event7 c 13 71
    mknod -m 640 ${D}${base_prefix}/dev/kmem c 1 2
    mknod -m 640 ${D}${base_prefix}/dev/mem c 1 1
    mknod -m 640 ${D}${base_prefix}/dev/mmcblk0 b 179 0
    mknod -m 640 ${D}${base_prefix}/dev/mmcblk0p1 b 179 1
    mknod -m 640 ${D}${base_prefix}/dev/mmcblk0p2 b 179 2
    mknod -m 640 ${D}${base_prefix}/dev/mmcblk0p3 b 179 3
    mknod -m 640 ${D}${base_prefix}/dev/mmcblk0p4 b 179 4
    mknod -m 640 ${D}${base_prefix}/dev/mmcblk0p5 b 179 5
    mknod -m 640 ${D}${base_prefix}/dev/mmcblk0p6 b 179 6
    mknod -m 640 ${D}${base_prefix}/dev/mmcblk0p7 b 179 7
    mknod -m 640 ${D}${base_prefix}/dev/mtd0 c 90 0
    mknod -m 640 ${D}${base_prefix}/dev/mtd1 c 90 2
    mknod -m 640 ${D}${base_prefix}/dev/mtd2 c 90 4
    mknod -m 640 ${D}${base_prefix}/dev/mtd3 c 90 6
    mknod -m 640 ${D}${base_prefix}/dev/mtd4 c 90 8
    mknod -m 640 ${D}${base_prefix}/dev/mtd5 c 90 10
    mknod -m 640 ${D}${base_prefix}/dev/mtd6 c 90 12
    mknod -m 640 ${D}${base_prefix}/dev/mtd7 c 90 14
    mknod -m 640 ${D}${base_prefix}/dev/mtdblock0 b 31 0
    mknod -m 640 ${D}${base_prefix}/dev/mtdblock1 b 31 1
    mknod -m 640 ${D}${base_prefix}/dev/mtdblock2 b 31 2
    mknod -m 640 ${D}${base_prefix}/dev/mtdblock3 b 31 3
    mknod -m 640 ${D}${base_prefix}/dev/mtdblock4 b 31 4
    mknod -m 640 ${D}${base_prefix}/dev/mtdblock5 b 31 5
    mknod -m 640 ${D}${base_prefix}/dev/mtdblock6 b 31 6
    mknod -m 640 ${D}${base_prefix}/dev/mtdblock7 b 31 7
    mknod -m 644 ${D}${base_prefix}/dev/null c 1 3
    mknod -m 644 ${D}${base_prefix}/dev/ptmx c 5 2
    mknod -m 640 ${D}${base_prefix}/dev/ram0 b 1 0
    mknod -m 640 ${D}${base_prefix}/dev/ram1 b 1 1
    mknod -m 640 ${D}${base_prefix}/dev/ram2 b 1 2
    mknod -m 640 ${D}${base_prefix}/dev/ram3 b 1 3
    mknod -m 644 ${D}${base_prefix}/dev/random c 1 8
    mknod -m 640 ${D}${base_prefix}/dev/tty c 4 0
    mknod -m 644 ${D}${base_prefix}/dev/tty1 c 4 1
    mknod -m 644 ${D}${base_prefix}/dev/tty2 c 4 2
    mknod -m 644 ${D}${base_prefix}/dev/tty3 c 4 3
    mknod -m 644 ${D}${base_prefix}/dev/tty4 c 4 4
    mknod -m 644 ${D}${base_prefix}/dev/tty5 c 4 5
    mknod -m 644 ${D}${base_prefix}/dev/tty6 c 4 6
    mknod -m 644 ${D}${base_prefix}/dev/tty7 c 4 7
    mknod -m 644 ${D}${base_prefix}/dev/tty8 c 4 8
    mknod -m 640 ${D}${base_prefix}/dev/ttyS0 c 4 64
    mknod -m 640 ${D}${base_prefix}/dev/ttySA0 c 204 5
    mknod -m 644 ${D}${base_prefix}/dev/urandom c 1 9
    mknod -m 644 ${D}${base_prefix}/dev/zero c 1 5
}

FILES_${PN} = "${base_prefix}/dev/apm_bios \
               ${base_prefix}/dev/console \
               ${base_prefix}/dev/fb0 \
               ${base_prefix}/dev/hda \
               ${base_prefix}/dev/hda1 \
               ${base_prefix}/dev/hda10 \
               ${base_prefix}/dev/hda11 \
               ${base_prefix}/dev/hda12 \
               ${base_prefix}/dev/hda13 \
               ${base_prefix}/dev/hda14 \
               ${base_prefix}/dev/hda15 \
               ${base_prefix}/dev/hda16 \
               ${base_prefix}/dev/hda17 \
               ${base_prefix}/dev/hda18 \
               ${base_prefix}/dev/hda19 \
               ${base_prefix}/dev/hda2 \
               ${base_prefix}/dev/hda3 \
               ${base_prefix}/dev/hda4 \
               ${base_prefix}/dev/hda5 \
               ${base_prefix}/dev/hda6 \
               ${base_prefix}/dev/hda7 \
               ${base_prefix}/dev/hda8 \
               ${base_prefix}/dev/hda9 \
               ${base_prefix}/dev/initctl \
               ${base_prefix}/dev/input/event0 \
               ${base_prefix}/dev/input/event1 \
               ${base_prefix}/dev/input/event2 \
               ${base_prefix}/dev/input/event3 \
               ${base_prefix}/dev/input/event4 \
               ${base_prefix}/dev/input/event5 \
               ${base_prefix}/dev/input/event6 \
               ${base_prefix}/dev/input/event7 \
               ${base_prefix}/dev/kmem \
               ${base_prefix}/dev/mem \
               ${base_prefix}/dev/mmcblk0 \
               ${base_prefix}/dev/mmcblk0p1 \
               ${base_prefix}/dev/mmcblk0p2 \
               ${base_prefix}/dev/mmcblk0p3 \
               ${base_prefix}/dev/mmcblk0p4 \
               ${base_prefix}/dev/mmcblk0p5 \
               ${base_prefix}/dev/mmcblk0p6 \
               ${base_prefix}/dev/mmcblk0p7 \
               ${base_prefix}/dev/mtd0 \
               ${base_prefix}/dev/mtd1 \
               ${base_prefix}/dev/mtd2 \
               ${base_prefix}/dev/mtd3 \
               ${base_prefix}/dev/mtd4 \
               ${base_prefix}/dev/mtd5 \
               ${base_prefix}/dev/mtd6 \
               ${base_prefix}/dev/mtd7 \
               ${base_prefix}/dev/mtdblock0 \
               ${base_prefix}/dev/mtdblock1 \
               ${base_prefix}/dev/mtdblock2 \
               ${base_prefix}/dev/mtdblock3 \
               ${base_prefix}/dev/mtdblock4 \
               ${base_prefix}/dev/mtdblock5 \
               ${base_prefix}/dev/mtdblock6 \
               ${base_prefix}/dev/mtdblock7 \
               ${base_prefix}/dev/null \
               ${base_prefix}/dev/ptmx \
               ${base_prefix}/dev/ram0 \
               ${base_prefix}/dev/ram1 \
               ${base_prefix}/dev/ram2 \
               ${base_prefix}/dev/ram3 \
               ${base_prefix}/dev/random \
               ${base_prefix}/dev/tty \
               ${base_prefix}/dev/tty0 \
               ${base_prefix}/dev/tty1 \
               ${base_prefix}/dev/tty2 \
               ${base_prefix}/dev/tty3 \
               ${base_prefix}/dev/tty4 \
               ${base_prefix}/dev/tty5 \
               ${base_prefix}/dev/tty6 \
               ${base_prefix}/dev/tty7 \
               ${base_prefix}/dev/tty8 \
               ${base_prefix}/dev/ttyS0 \
               ${base_prefix}/dev/ttySA0 \
               ${base_prefix}/dev/urandom \
               ${base_prefix}/dev/zero"

INHIBIT_PACKAGE_DEBUG_SPLIT_${PN} = "1" 
INSANE_SKIP_${PN} += "ldflags" 
