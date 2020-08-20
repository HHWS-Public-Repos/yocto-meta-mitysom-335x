# symlinks fonts so qt can find them.

fonts_fixup () {
	pushd ${IMAGE_ROOTFS}
	echo ${IMAGE_ROOTFS} >&2
	ln -snfr usr/share/fonts/ttf/ usr/lib/fonts
	popd
}

ROOTFS_POSTPROCESS_COMMAND += "fonts_fixup; "
