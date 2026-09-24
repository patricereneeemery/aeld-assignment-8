#!/bin/bash

# Source the Yocto environment
source poky/oe-init-build-env build

# Run QEMU with the correct machine
runqemu qemux86-64
