/**
 * @file aesd-circular-buffer.c
 * @brief Functions and data related to a circular buffer implementation
 */

#ifdef __KERNEL__
#include <linux/string.h>
#else
#include <string.h>
#endif

#include "aesd-circular-buffer.h"

struct aesd_buffer_entry *aesd_circular_buffer_find_entry_offset_for_fpos(
        struct aesd_circular_buffer *buffer,
        size_t char_offset,
        size_t *entry_offset_byte_rtn)
{
    size_t cur_offs;
    size_t bytes_counted;
    size_t i;
    struct aesd_buffer_entry *entry;

    cur_offs = buffer->out_offs;
    bytes_counted = 0;

    for (i = 0; i < AESDCHAR_MAX_WRITE_OPERATIONS_SUPPORTED; i++) {

        if (!buffer->full && cur_offs == buffer->in_offs)
            break;

        /* NOTE: header likely defines `entry[]`, not `entries[]` */
        entry = &buffer->entry[cur_offs];

        if (char_offset < bytes_counted + entry->size) {
            *entry_offset_byte_rtn = char_offset - bytes_counted;
            return entry;
        }

        bytes_counted += entry->size;
        cur_offs = (cur_offs + 1) % AESDCHAR_MAX_WRITE_OPERATIONS_SUPPORTED;
    }

    return NULL;
}

void aesd_circular_buffer_add_entry(struct aesd_circular_buffer *buffer,
                                    const struct aesd_buffer_entry *add_entry)
{
    /* Write new entry at current in_offs */
    buffer->entry[buffer->in_offs] = *add_entry;

    if (buffer->full) {
        buffer->out_offs =
            (buffer->out_offs + 1) % AESDCHAR_MAX_WRITE_OPERATIONS_SUPPORTED;
    }

    buffer->in_offs =
        (buffer->in_offs + 1) % AESDCHAR_MAX_WRITE_OPERATIONS_SUPPORTED;

    if (buffer->in_offs == buffer->out_offs) {
        buffer->full = true;
    }
}

void aesd_circular_buffer_init(struct aesd_circular_buffer *buffer)
{
    memset(buffer, 0, sizeof(struct aesd_circular_buffer));
}

bool aesd_circular_buffer_empty(struct aesd_circular_buffer *buffer)
{
    return (!buffer->full && buffer->in_offs == buffer->out_offs);
}

