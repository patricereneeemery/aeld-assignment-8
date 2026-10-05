DESCRIPTION = "AESD Custom Image"
LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL:append = " aesd-char-driver"
