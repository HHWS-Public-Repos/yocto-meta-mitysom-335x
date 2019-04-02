DESCRIPTION = "Units to initialize usb gadgets"


do_install() {
        install -d ${D}${base_libdir}/systemd/system/
        install -m 0644 ${WORKDIR}/*.service ${D}${base_libdir}/systemd/system

	#Delete storage-gadget-init.service as it was causing issues when booting from NAND.
	rm ${D}${base_libdir}/systemd/system/storage-gadget-init.service

        install -d ${D}${sysconfdir}/udev/rules.d
        install -m 0644 ${WORKDIR}/*.rules ${D}${sysconfdir}/udev/rules.d
        install -m 0644 ${WORKDIR}/*.conf ${D}${sysconfdir}

        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/*.sh ${D}${bindir}
}


FILES_${PN}-storage = "${bindir}/g-storage-reinsert.sh \
                       ${bindir}/update-image-info-on-mmcblk0p1.sh \
                       #${base_libdir}/systemd/system/storage-gadget-init.service \
                       ${sysconfdir}/udev/rules.d/bone-gmass-eject.rules"
