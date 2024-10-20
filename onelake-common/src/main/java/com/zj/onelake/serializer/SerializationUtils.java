package com.zj.onelake.serializer;

import org.apache.flink.core.memory.MemorySegmentFactory;
import org.apache.flink.table.data.binary.BinaryRowData;
import org.apache.flink.table.data.binary.BinarySegmentUtils;

import java.nio.ByteBuffer;

import static org.apache.flink.table.data.binary.BinarySegmentUtils.copyToBytes;

/**
 * @Author geniuszhang
 * @Date 2024/10/20 10:20
 * @Description: TODO
 * @Version 1.0
 */
public class SerializationUtils {
    /**
     * Serialize binary row data.
     *
     * @param binaryRowData
     * @return
     */
    public static byte[] serializeBinaryRowData(BinaryRowData binaryRowData) {
        byte[] bytes = copyToBytes(binaryRowData.getSegments(), binaryRowData.getOffset(), binaryRowData.getSizeInBytes());
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + bytes.length);
        byteBuffer.putInt(binaryRowData.getArity()).put(bytes);
        return byteBuffer.array();
    }

    /**
     * Deserialize binary row data.
     *
     * @param bytes
     * @return
     */
    public static BinaryRowData deserializeBinaryRowData(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        int artity = byteBuffer.getInt();
        BinaryRowData binaryRowData = new BinaryRowData(artity);
        binaryRowData.pointTo(MemorySegmentFactory.wrap(bytes), 4, bytes.length - 4);
        return binaryRowData;
    }
}
