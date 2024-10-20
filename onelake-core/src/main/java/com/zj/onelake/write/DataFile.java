package com.zj.onelake.write;

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
                            new RowType.RowField("null_value_count", new BigIntType(false)),
                            new RowType.RowField("nan_value_count", new BigIntType(false)),
                            new RowType.RowField("lower_bounds", new VarBinaryType(false, VarBinaryType.MAX_LENGTH)),
                            new RowType.RowField("upper_bounds", new VarBinaryType(false, VarBinaryType.MAX_LENGTH)),
                            new RowType.RowField("min_sequence_number", new BigIntType(false)),
                            new RowType.RowField("max_sequence_number", new BigIntType(false)),
                            new RowType.RowField("level", new IntType(false)),
                            new RowType.RowField("create_time", new BigIntType(false)),
                            new RowType.RowField("schema_id", new IntType(false))));


}
