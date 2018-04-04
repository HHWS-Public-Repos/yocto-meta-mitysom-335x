
do_install_append() {
	mkdir -p "${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/sgx"
	for module in ${D}/lib/modules/${KERNEL_VERSION}/extra/*.ko; do
		ln -sf "$module" "${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/sgx/$(basename "$module")"
	done
}

FILES_${PN} += "/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/sgx/*.ko"
