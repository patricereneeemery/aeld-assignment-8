#!/bin/bash
<<<<<<< HEAD
# Script to build image for qemu.
# Author: Siddhant Jajoo.

=======
# Script to build buildroot configuration
# Author: Siddhant Jajoo
#Modified by Patrice Emery

source shared.sh

EXTERNAL_REL_BUILDROOT=../base_external
>>>>>>> 33dc89a43bb3c3ae27fd07beae865fe31ad1fa3b
git submodule init
git submodule sync
git submodule update

<<<<<<< HEAD
# local.conf won't exist until this step on first execution
#source poky/oe-init-build-env
source poky/oe-init-build-env build

# CONFLINE="MACHINE = \"qemuarm64\""   # old ECEN default
CONFLINE="MACHINE = \"qemux86-64\""    # AESD required architecture



cat conf/local.conf | grep "${CONFLINE}" > /dev/null
local_conf_info=$?

if [ $local_conf_info -ne 0 ];then
	echo "Append ${CONFLINE} in the local.conf file"
	echo ${CONFLINE} >> conf/local.conf
	
else
	echo "${CONFLINE} already exists in the local.conf file"
fi


bitbake-layers show-layers | grep "meta-aesd" > /dev/null
layer_info=$?

if [ $layer_info -ne 0 ];then
	echo "Adding meta-aesd layer"
	bitbake-layers add-layer ../meta-aesd
else
	echo "meta-aesd layer already exists"
fi

set -e
bitbake core-image-aesd
=======
set -e
cd `dirname $0`

if [ ! -e buildroot/.config ]
then
    echo "MISSING BUILDROOT CONFIGURATION FILE"

    if [ -e ${AESD_MODIFIED_DEFCONFIG} ]
    then
        echo "USING ${AESD_MODIFIED_DEFCONFIG}"
        make -C buildroot defconfig BR2_EXTERNAL=${EXTERNAL_REL_BUILDROOT} BR2_DEFCONFIG=${AESD_MODIFIED_DEFCONFIG_REL_BUILDROOT}
    else
        echo "Run ./save_config.sh to save this as the default configuration in ${AESD_MODIFIED_DEFCONFIG}"
        echo "Then add packages as needed to complete the installation, re-running ./save_config.sh as needed"
        make -C buildroot defconfig BR2_EXTERNAL=${EXTERNAL_REL_BUILDROOT} BR2_DEFCONFIG=${AESD_DEFAULT_DEFCONFIG}
    fi
else
    echo "USING EXISTING BUILDROOT CONFIG"
    echo "To force update, delete .config or make changes using make menuconfig and build again."
    make -C buildroot BR2_EXTERNAL=${EXTERNAL_REL_BUILDROOT}
fi

# *** THIS IS THE CRITICAL MISSING STEP ***
# This actually builds Buildroot and produces output/images/
make -C buildroot
>>>>>>> 33dc89a43bb3c3ae27fd07beae865fe31ad1fa3b
