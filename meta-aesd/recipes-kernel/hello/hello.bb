DESCRIPTION = "hello module from ldd3"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"

SRC_URI = "file://Makefile \
           file://hello.c \
           file://LICENSE"

S = "${WORKDIR}"

inherit module

EXTRA_OEMAKE += " -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/misc
    install -m 0644 hello.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/misc/hello.ko
}

FILES:kernel-module-hello = "/lib/modules/${KERNEL_VERSION}/kernel/drivers/misc/hello.ko"
