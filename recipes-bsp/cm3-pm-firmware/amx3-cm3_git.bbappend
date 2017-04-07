do_install_append(){
	install -m 0644 bin/am335x-pm-firmware.bin ${D}${base_libdir}/firmware/	
}
