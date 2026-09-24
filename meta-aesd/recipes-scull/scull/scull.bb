LICENSE = "GPLv2"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=751419260aa954499f7abaabaa882bbe"
LIC_FILES_CHKSUM = "file://LICENSE;md5=855cb65b5f2c39e6d931fdfc25379c91"

PV = "1.0"

SRC_URI = "file:///home/patrice/assignment-7-patricereneeemery/scull.tar.gz"

S = "${WORKDIR}/scull"

inherit module

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/scull.ko ${D}/lib/modules/${KERNEL_VERSION}/extra/
}

FILES_${PN} += "/lib/modules/${KERNEL_VERSION}/extra/scull.ko"
