package com.zj.onelake.sink;

import com.zj.onelake.CoreOptions;
import com.zj.onelake.table.DataTable;

import com.zj.onelake.write.Committable;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.sink.DiscardingSink;
import org.apache.flink.streaming.api.transformations.PartitionTransformation;
import org.apache.flink.table.data.RowData;
import org.apache.iceberg.Table;
import org.apache.iceberg.flink.TableLoader;

import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;

/**
 * OneLake sink.
 *
 * @Author geniuszhang
 * @Date 2024/11/30 15:50
 * @Description: TODO
 * @Version 1.0
 */
public class OneLakeSink implements Serializable {
    private static final long serialVersionUID = -1L;

    private static final String DUMMY_SINK_NAME = "DummySink";

    private final TableLoader tableLoader;

    private final DataStream<RowData> input;

    private final Configuration dynamicOptions;

    public OneLakeSink(TableLoader tableLoader, DataStream<RowData> input, Configuration dynamicOptions) {
        this.tableLoader = tableLoader;
        this.input = input;
        this.dynamicOptions = dynamicOptions;
    }

    public DataStreamSink<?> append() {
        Table table = loadTable(tableLoader);

        com.zj.onelake.table.Table dataTable = new DataTable(tableLoader).copy(dynamicOptions.toMap());
        CoreOptions options = CoreOptions.fromMap(dataTable.options());

        SingleOutputStreamOperator<Committable> writerStream = appendWriter(dataTable, options);

        DataStream<Void> committableStream = null;

        return appendDummySink(committableStream, dataTable);
    }

    private SingleOutputStreamOperator<Committable> appendWriter(
            com.zj.onelake.table.Table dataTable,
            CoreOptions options) {

        PartitionTransformation<RowData> partitioned =
                new PartitionTransformation<>(input.getTransformation(), null);

        DataStream<RowData> distributeStream =
                new DataStream<>(input.getExecutionEnvironment(), partitioned);

        return null;
    }

    private <T> DataStreamSink<T> appendDummySink(
            DataStream<T> dataStream, com.zj.onelake.table.Table dataTable) {
        return dataStream
                .addSink(new DiscardingSink<>())
                .name(DUMMY_SINK_NAME + ":" + dataTable.name())
                .setParallelism(1);
    }

    private static Table loadTable(TableLoader tableLoader) {
        tableLoader.open();
        try (TableLoader loader = tableLoader) {
            return loader.loadTable();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to load onelake table from table loader: " + tableLoader, e);
        }
    }
}
