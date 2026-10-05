EXTRA_CFLAGS += -I$(M)

obj-m += aesd-char-driver.o

aesd-char-driver-objs := aesdchar.o aesd-circular-buffer.o

all:
	make -C $(KERNEL_SRC) M=$(PWD) modules

clean:
	make -C $(KERNEL_SRC) M=$(PWD) clean
