DESCRIPTION = "misc-modules init script only"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"

SRC_URI = "file://miscmodules.init \
           file://LICENSE"

S = "${WORKDIR}"

# IMPORTANT: misc-modules is NOT a module recipe
# It should NOT autoload any kernel modules
# It should NOT depend on faulty, hello, or scull

do_install() {
    install -d ${D}/etc/init.d
    install -m 0755 miscmodules.init ${D}/etc/init.d/miscmodules
}

FILES:${PN} += "/etc/init.d/miscmodules"

# Prevent Yocto from auto-adding module dependencies
RDEPENDS:${PN} = ""
