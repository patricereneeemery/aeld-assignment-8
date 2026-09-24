SUMMARY = "AESD assignment 6 socket server"
DESCRIPTION = "AESD assignment 6 socket server"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-patricereneeemery.git;protocol=https;branch=assignment6"
SRC_URI += "file://aesdsocket.service"

SRCREV = "386752bfe52f1ce911414d98c6a01a34fff2a2c0"

# aesdsocket.c lives in server/
S = "${WORKDIR}/git/server"

inherit systemd

do_configure() {
    :
}

do_compile() {
    oe_runmake -C ${S}
}

do_install() {
    # Install aesdsocket binary
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/aesdsocket

    # Install systemd service file
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/aesdsocket.service ${D}${systemd_system_unitdir}/aesdsocket.service
}

FILES:${PN} = "\
    ${bindir}/aesdsocket \
    ${systemd_system_unitdir}/aesdsocket.service \
"

SYSTEMD_SERVICE:${PN} = "aesdsocket.service"
