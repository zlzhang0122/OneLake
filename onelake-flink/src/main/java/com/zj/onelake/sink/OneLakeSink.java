package com.zj.onelake.sink;

import com.zj.onelake.table.DataTable;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.functions.sink.DiscardingSink;
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

    private final Configuration dynamicOptions;

    public OneLakeSink(TableLoader tableLoader, Configuration dynamicOptions) {
        this.tableLoader = tableLoader;
        this.dynamicOptions = dynamicOptions;
    }

    public DataStreamSink<?> append() {
        Table table = loadTable(tableLoader);

        com.zj.onelake.table.Table dataTable = new DataTable(tableLoader).copy(dynamicOptions.toMap());

        DataStream<Void> committableStream = null;

        return appendDummySink(committableStream, dataTable);
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
