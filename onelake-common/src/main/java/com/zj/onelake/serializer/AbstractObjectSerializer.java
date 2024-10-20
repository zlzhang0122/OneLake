package com.zj.onelake.serializer;

import org.apache.flink.table.runtime.typeutils.InternalSerializers;
import org.apache.flink.table.runtime.typeutils.RowDataSerializer;
import org.apache.flink.table.types.logical.RowType;

import java.io.Serializable;

/**
 * @Author geniuszhang
 * @Date 2024/10/20 09:52
 * @Description: TODO
 * @Version 1.0
 */
public class AbstractObjectSerializer<T> implements Serializable {
    private final RowDataSerializer rowDataSerializer;

    public AbstractObjectSerializer(RowType rowType) {
        this.rowDataSerializer = InternalSerializers.create(rowType);
    }

    public int numFields() {
        return rowDataSerializer.getArity();
    }
}
