SUMMARY = "Very high-quality data compression program."
DESCRIPTION = "bzip2 compresses files using the Burrows-Wheeler block-sorting text compression algorithm, and \
Huffman coding. Compression is generally considerably better than that achieved by more conventional \
LZ77/LZ78-based compressors, and approaches the performance of the PPM family of statistical compressors."
HOMEPAGE = "http://www.bzip.org/"
SECTION = "console/utils"
LICENSE = "bzip2"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=8;endline=37;md5=40d9d1eb05736d1bfc86cfdd9106e6b2"
PR = "r5"

SRC_URI = "http://www.bzip.org/${PV}/${BPN}-${PV}.tar.gz \
           file://configure.ac \
           file://run-ptest \
	   file://Makefile.am"

SRC_URI[md5sum] = "00b516f4704d4a7cb50a1d97e6e8e15b"
SRC_URI[sha256sum] = "a2848f34fcd5d6cf47def00461fcb528a0484d8edef8208d6d2e2909dc61d9cd"

PACKAGES =+ "libbz2 libbz2-dev libbz2-staticdev"

CFLAGS_append = " -fPIC -fpic -Winline -fno-strength-reduce -D_FILE_OFFSET_BITS=64"

inherit autotools update-alternatives ptest

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_${PN} = "bunzip2 bzcat"

#install binaries to bzip2-native under sysroot for replacement-native
EXTRA_OECONF_append_class-native = " --bindir=${STAGING_BINDIR_NATIVE}/${PN}"
do_configure_prepend () {
	cp ${WORKDIR}/configure.ac ${S}/
	cp ${WORKDIR}/Makefile.am ${S}/
}

do_install_ptest () {
	sed -i -e "s|^Makefile:|_Makefile:|" ${D}${PTEST_PATH}/Makefile
}

FILES_libbz2 = "${libdir}/lib*${SOLIBS}"

FILES_libbz2-dev = "${includedir} ${libdir}/lib*${SOLIBSDEV}"
SECTION_libbz2-dev = "devel"
RDEPENDS_libbz2-dev = "libbz2 (= ${EXTENDPKGV})"

FILES_libbz2-staticdev = "${libdir}/*.a"
SECTION_libbz2-staticdev = "devel"
RDEPENDS_libbz2-staticdev = "libbz2-dev (= ${EXTENDPKGV})"

PROVIDES_append_class-native = " bzip2-replacement-native"
BBCLASSEXTEND = "native nativesdk"

