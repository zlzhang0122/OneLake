package com.zj.onelake.sink;

import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.iceberg.flink.TableLoader;

import java.io.Serializable;

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

    private final TableLoader tableLoader;

    public OneLakeSink(TableLoader tableLoader) {
        this.tableLoader = tableLoader;
    }

    public DataStreamSink<?> append() {
        return null;
    }
}
