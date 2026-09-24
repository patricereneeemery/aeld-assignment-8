obj-m += faulty.o
obj-m += hello.o

KERNEL_SRC ?= $(KERNELDIR)

all:
	$(MAKE) -C $(KERNEL_SRC) M=$(PWD) modules

clean:
	$(MAKE) -C $(KERNEL_SRC) M=$(PWD) clean
