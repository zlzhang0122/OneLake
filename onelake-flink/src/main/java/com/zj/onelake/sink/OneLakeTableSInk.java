package com.zj.onelake.sink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.sink.DataStreamSinkProvider;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.data.RowData;
import org.apache.iceberg.flink.TableLoader;

import static org.apache.flink.util.Preconditions.checkState;

/**
 * @Author geniuszhang
 * @Date 2024/11/30 15:59
 * @Description: TODO
 * @Version 1.0
 */
public class OneLakeTableSInk implements DynamicTableSink {

    private final TableLoader tableLoader;

    public OneLakeTableSInk(TableLoader tableLoader) {
        this.tableLoader = tableLoader;
    }

    public ChangelogMode getChangelogMode(ChangelogMode changelogMode) {
        return changelogMode;
    }

    public SinkRuntimeProvider getSinkRuntimeProvider(DynamicTableSink.Context context) {
        checkState(!context.isBounded(), "Currently, we just support streaming mode!");

        return new DataStreamSinkProvider() {
            public DataStreamSink<?> consumeDataStream(DataStream<RowData> dataStream) {
                OneLakeSink oneLakeSink =
                        new OneLakeSink(tableLoader);
                return oneLakeSink.append();
            }
        };
    }

    public DynamicTableSink copy() {
        return new OneLakeTableSInk(tableLoader);
    }

    public String asSummaryString() {
        return "OneLakeTableSink";
    }
}
