DESCRIPTION = "Common X11 scripts and configuration files"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
SECTION = "x11"
PR = "r47"

SRC_URI = "file://etc \
           file://gplv2-license.patch"

S = "${WORKDIR}"

do_install() {
	cp -R ${S}/etc ${D}${sysconfdir}
	chmod -R 755 ${D}${sysconfdir}
	find ${D}${sysconfdir} -type f -name \*~ -exec rm -rf {} \;
}

RDEPENDS_${PN} = "dbus-x11 xmodmap xdpyinfo xtscal xinit formfactor"

