#!/bin/sh

BR2_EXTERNAL=../base_external make savedefconfig
cp buildroot/defconfig base_external/configs/aesd_qemu_defconfig
