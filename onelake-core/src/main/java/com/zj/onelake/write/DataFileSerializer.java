package com.zj.onelake.write;

import com.zj.onelake.serializer.AbstractObjectSerializer;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.binary.BinaryStringData;
import org.apache.flink.table.types.logical.RowType;

import static com.zj.onelake.serializer.SerializationUtils.deserializeBinaryRowData;
import static com.zj.onelake.serializer.SerializationUtils.serializeBinaryRowData;

/**
 * For serialize/deserialize data file.
 *
 * @Author geniuszhang
 * @Date 2024/10/20 09:51
 * @Description: TODO
 * @Version 1.0
 */
public class DataFileSerializer extends AbstractObjectSerializer {
    private static final long serialVersionUID = 1L;

    public DataFileSerializer(RowType rowType) {
        super(DataFile.SCHEMA);
    }

    public RowData toRow(DataFile dataFile) {
        return GenericRowData.of(
                BinaryStringData.fromString(dataFile.getFileName()),
                dataFile.getFileSizeBytes(),
                dataFile.getRowCount(),
                dataFile.getRowCountDelete(),
                serializeBinaryRowData(dataFile.getMinKey()),
                serializeBinaryRowData(dataFile.getMaxKey()),
                serializeBinaryRowData(dataFile.getNullValueCount()),
                serializeBinaryRowData(dataFile.getNanValueCount()),
                serializeBinaryRowData(dataFile.getLowerBounds()),
                serializeBinaryRowData(dataFile.getUpperBounds()),
                dataFile.getMinSequenceNumber(),
                dataFile.getMaxSequenceNumber(),
                dataFile.getLevel(),
                dataFile.getCreateTime(),
                dataFile.getSchemaId());
    }

    public DataFile fromRow(RowData rowData) {
        return new DataFile(
                rowData.getString(0).toString(),
                rowData.getLong(1),
                rowData.getLong(2),
                rowData.getLong(3),
                deserializeBinaryRowData(rowData.getBinary(4)),
                deserializeBinaryRowData(rowData.getBinary(5)),
                deserializeBinaryRowData(rowData.getBinary(6)),
                deserializeBinaryRowData(rowData.getBinary(7)),
                deserializeBinaryRowData(rowData.getBinary(8)),
                deserializeBinaryRowData(rowData.getBinary(9)),
                rowData.getLong(10),
                rowData.getLong(11),
                rowData.getInt(12),
                rowData.getLong(13),
                rowData.getInt(14));
    }
}
