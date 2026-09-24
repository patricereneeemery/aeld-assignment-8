<<<<<<< HEAD
#!/bin/bash

# Source the Yocto environment
source poky/oe-init-build-env build

# Run QEMU with the correct machine
runqemu qemux86-64
=======
#!/bin/sh

KERNEL=buildroot/output/images/bzImage
ROOTFS=buildroot/output/images/rootfs.ext2

NET_OPTS="-netdev user,id=net0,hostfwd=tcp::9000-:9000 -device virtio-net-pci,netdev=net0"

qemu-system-x86_64 \
    -M q35 \
    -m 256M \
    -kernel $KERNEL \
    -drive file=$ROOTFS,format=raw,if=virtio \
    -append "root=/dev/vda console=ttyS0" \
    -nographic \
    $NET_OPTS
>>>>>>> 33dc89a43bb3c3ae27fd07beae865fe31ad1fa3b
