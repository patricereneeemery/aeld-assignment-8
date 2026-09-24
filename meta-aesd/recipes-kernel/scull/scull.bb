DESCRIPTION = "SCULL character device driver from ldd3"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

PV = "1.0"

SRC_URI = "file://Makefile \
           file://main.c \
           file://access.c \
           file://pipe.c \
           file://scull.h \
           file://access_ok_version.h \
           file://proc_ops_version.h \
           file://scull_load \
           file://scull_unload \
           file://scull.init"

S = "${WORKDIR}"

inherit module

EXTRA_OEMAKE += " -C ${STAGING_KERNEL_BUILDDIR} M=${S}"

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/misc
    install -m 0644 scull.ko ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/misc/scull.ko

    install -d ${D}/etc/init.d
    install -m 0755 scull.init ${D}/etc/init.d/scull
}

FILES:kernel-module-scull = "/lib/modules/${KERNEL_VERSION}/kernel/drivers/misc/scull.ko"
FILES:${PN} += "/etc/init.d/scull"
