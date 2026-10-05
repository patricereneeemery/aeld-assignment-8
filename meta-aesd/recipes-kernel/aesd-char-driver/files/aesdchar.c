#include <linux/init.h>
#include <linux/module.h>
#include <linux/fs.h>
#include <linux/uaccess.h>
#include <linux/slab.h>
#include <linux/cdev.h>
#include <linux/device.h>
#include "aesd-circular-buffer.h"

MODULE_LICENSE("Dual BSD/GPL");
MODULE_AUTHOR("Patrice Emery");
MODULE_DESCRIPTION("AESD Assignment 8 Character Driver");

static struct aesd_circular_buffer buffer;
static DEFINE_MUTEX(aesd_mutex);

static dev_t aesd_dev;
static struct cdev aesd_cdev;
static struct class *aesd_class;

static int aesd_open(struct inode *inode, struct file *filp)
{
    return 0;
}

static int aesd_release(struct inode *inode, struct file *filp)
{
    return 0;
}

static ssize_t aesd_read(struct file *filp, char __user *buf,
                         size_t count, loff_t *f_pos)
{
    size_t entry_offset;
    const struct aesd_buffer_entry *entry;
    ssize_t retval = 0;

    mutex_lock(&aesd_mutex);

    entry = aesd_circular_buffer_find_entry_offset_for_fpos(
        &buffer, *f_pos, &entry_offset);

    if (!entry) {
        retval = 0;
        goto out;
    }

    if (count > entry->size - entry_offset)
        count = entry->size - entry_offset;

    if (copy_to_user(buf, entry->buffptr + entry_offset, count)) {
        retval = -EFAULT;
        goto out;
    }

    *f_pos += count;
    retval = count;

out:
    mutex_unlock(&aesd_mutex);
    return retval;
}

static ssize_t aesd_write(struct file *filp, const char __user *buf,
                          size_t count, loff_t *f_pos)
{
    char *kbuf;
    struct aesd_buffer_entry entry;

    kbuf = kmalloc(count, GFP_KERNEL);
    if (!kbuf)
        return -ENOMEM;

    if (copy_from_user(kbuf, buf, count)) {
        kfree(kbuf);
        return -EFAULT;
    }

    entry.buffptr = kbuf;
    entry.size = count;

    mutex_lock(&aesd_mutex);
    aesd_circular_buffer_add_entry(&buffer, &entry);
    mutex_unlock(&aesd_mutex);

    return count;
}

static struct file_operations aesd_fops = {
    .owner = THIS_MODULE,
    .read = aesd_read,
    .write = aesd_write,
    .open = aesd_open,
    .release = aesd_release,
};

static int __init aesd_init(void)
{
    int result;

    aesd_circular_buffer_init(&buffer);

    /* Allocate a dynamic major/minor */
    result = alloc_chrdev_region(&aesd_dev, 0, 1, "aesdchar");
    if (result < 0) {
        pr_err("alloc_chrdev_region failed\n");
        return result;
    }

    /* Initialize and add cdev */
    cdev_init(&aesd_cdev, &aesd_fops);
    aesd_cdev.owner = THIS_MODULE;

    result = cdev_add(&aesd_cdev, aesd_dev, 1);
    if (result < 0) {
        pr_err("cdev_add failed\n");
        unregister_chrdev_region(aesd_dev, 1);
        return result;
    }

    /* Create class */
    aesd_class = class_create(THIS_MODULE, "aesd");
    if (IS_ERR(aesd_class)) {
        pr_err("class_create failed\n");
        cdev_del(&aesd_cdev);
        unregister_chrdev_region(aesd_dev, 1);
        return PTR_ERR(aesd_class);
    }

    /* Create device node /dev/aesdchar */
    if (!device_create(aesd_class, NULL, aesd_dev, NULL, "aesdchar")) {
        pr_err("device_create failed\n");
        class_destroy(aesd_class);
        cdev_del(&aesd_cdev);
        unregister_chrdev_region(aesd_dev, 1);
        return -ENOMEM;
    }

    pr_info("aesdchar device created successfully\n");
    return 0;
}

static void __exit aesd_exit(void)
{
    device_destroy(aesd_class, aesd_dev);
    class_destroy(aesd_class);
    cdev_del(&aesd_cdev);
    unregister_chrdev_region(aesd_dev, 1);
}

module_init(aesd_init);
module_exit(aesd_exit);
