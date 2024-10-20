package com.zj.onelake.write;

import org.apache.flink.table.data.binary.BinaryRowData;
import org.apache.flink.table.types.logical.BigIntType;
import org.apache.flink.table.types.logical.IntType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.table.types.logical.VarBinaryType;
import org.apache.flink.table.types.logical.VarCharType;

import java.util.Arrays;

/**
 * Data file.
 *
 * @Author geniuszhang
 * @Date 2024/10/20 09:22
 * @Description: TODO
 * @Version 1.0
 */
public class DataFile {
    /**
     * Data file schema.
     */
    public static final RowType SCHEMA =
            new RowType(
                    false,
                    Arrays.asList(
                            new RowType.RowField("file_name", new VarCharType(false, VarCharType.MAX_LENGTH)),
                            new RowType.RowField("file_size_bytes", new BigIntType(false)),
                            new RowType.RowField("row_count", new BigIntType(false)),
                            new RowType.RowField("row_count_delete", new BigIntType(false)),
                            new RowType.RowField("min_key", new VarBinaryType(false, VarBinaryType.MAX_LENGTH)),
                            new RowType.RowField("max_key", new VarBinaryType(false, VarBinaryType.MAX_LENGTH)),
                            new RowType.RowField("null_value_count", new VarBinaryType(false, VarBinaryType.MAX_LENGTH)),
                            new RowType.RowField("nan_value_count", new VarBinaryType(false, VarBinaryType.MAX_LENGTH)),
                            new RowType.RowField("lower_bounds", new VarBinaryType(false, VarBinaryType.MAX_LENGTH)),
                            new RowType.RowField("upper_bounds", new VarBinaryType(false, VarBinaryType.MAX_LENGTH)),
                            new RowType.RowField("min_sequence_number", new BigIntType(false)),
                            new RowType.RowField("max_sequence_number", new BigIntType(false)),
                            new RowType.RowField("level", new IntType(false)),
                            new RowType.RowField("create_time", new BigIntType(false)),
                            new RowType.RowField("schema_id", new IntType(false))));

    private final String fileName;
    // total size of data in bytes.
    private final long fileSizeBytes;
    // total number of rows(add and delete).
    private final long rowCount;
    // total delete number of rows(only delete).
    private final long rowCountDelete;
    private final BinaryRowData minKey;
    private final BinaryRowData maxKey;
    private final BinaryRowData nullValueCount;
    private final BinaryRowData nanValueCount;
    private final BinaryRowData lowerBounds;
    private final BinaryRowData upperBounds;
    private final long minSequenceNumber;
    private final long maxSequenceNumber;
    private final int level;
    private final long createTime;
    private final int schemaId;

    public DataFile(
            String fileName,
            long fileSizeBytes,
            long rowCount,
            long rowCountDelete,
            BinaryRowData minKey,
            BinaryRowData maxKey,
            BinaryRowData nullValueCount,
            BinaryRowData nanValueCount,
            BinaryRowData lowerBounds,
            BinaryRowData upperBounds,
            long minSequenceNumber,
            long maxSequenceNumber,
            int level,
            long createTime,
            int schemaId) {
        this.fileName = fileName;
        this.fileSizeBytes = fileSizeBytes;
        this.rowCount = rowCount;
        this.rowCountDelete = rowCountDelete;

        this.minKey = minKey;
        this.maxKey = maxKey;
        this.nullValueCount = nullValueCount;
        this.nanValueCount = nanValueCount;
        this.lowerBounds = lowerBounds;
        this.upperBounds = upperBounds;
        this.minSequenceNumber = minSequenceNumber;
        this.maxSequenceNumber = maxSequenceNumber;

        this.createTime = createTime;
        this.level = level;
        this.schemaId = schemaId;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public long getRowCount() {
        return rowCount;
    }

    public long getRowCountDelete() {
        return rowCountDelete;
    }

    public BinaryRowData getMinKey() {
        return minKey;
    }

    public BinaryRowData getMaxKey() {
        return maxKey;
    }

    public BinaryRowData getNullValueCount() {
        return nullValueCount;
    }

    public BinaryRowData getNanValueCount() {
        return nanValueCount;
    }

    public BinaryRowData getLowerBounds() {
        return lowerBounds;
    }

    public BinaryRowData getUpperBounds() {
        return upperBounds;
    }

    public long getMinSequenceNumber() {
        return minSequenceNumber;
    }

    public long getMaxSequenceNumber() {
        return maxSequenceNumber;
    }

    public int getLevel() {
        return level;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getSchemaId() {
        return schemaId;
    }
}
