DESCRIPTION = "AESD assignments package"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "file://full-test.sh"
SRC_URI += "file://assignment.txt"
SRC_URI += "file://assignment-test.sh"

S = "${WORKDIR}"

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${WORKDIR}/full-test.sh ${D}/usr/bin/full-test.sh

    install -d ${D}/usr/bin/conf
    install -m 0644 ${WORKDIR}/assignment.txt ${D}/usr/bin/conf/assignment.txt

    # Assignment 7 test harness
    install -m 0755 ${WORKDIR}/assignment-test.sh ${D}/usr/bin/assignment-test
}

FILES:${PN} += "/usr/bin/full-test.sh"
FILES:${PN} += "/usr/bin/conf"
FILES:${PN} += "/usr/bin/conf/assignment.txt"
FILES:${PN} += "/usr/bin/assignment-test"

RDEPENDS:${PN} += "bash"
