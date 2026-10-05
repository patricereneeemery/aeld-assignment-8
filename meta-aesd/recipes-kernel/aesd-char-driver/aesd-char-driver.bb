DESCRIPTION = "AESD Character Driver"
LICENSE = "CLOSED"

SRC_URI = "file://aesdchar.c \
           file://aesd-circular-buffer.c \
           file://aesd-circular-buffer.h \
           file://Makefile"

S = "${WORKDIR}"

inherit module

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0644 aesd-char-driver.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
}
