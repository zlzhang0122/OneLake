package com.zj.onelake.index;

/**
 * Index type enum.
 *
 * @Author geniuszhang
 * @Date 2024/12/8 21:31
 * @Description: TODO
 * @Version 1.0
 */
public enum IndexTypeEnum {
    DELETION_VECTORS(0);

    private final int id;

    IndexTypeEnum(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public static IndexTypeEnum fromId(int id) {
        if (id == 0) {
            return IndexTypeEnum.DELETION_VECTORS;
        }
        throw new UnsupportedOperationException("Unsupported status id '" + id + "'.");
    }
}
