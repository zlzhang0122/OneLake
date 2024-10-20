package com.zj.onelake;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.factories.DynamicTableSinkFactory;
import org.apache.flink.table.factories.DynamicTableSourceFactory;

import java.util.Set;

/**
 * @Author geniuszhang
 * @Date 2024/10/20 09:10
 * @Description: TODO
 * @Version 1.0
 */
public class OneLakeTableFactory implements DynamicTableSourceFactory, DynamicTableSinkFactory {
    public static final String FACTORY_IDENTIFIER = "onelake";

    public DynamicTableSink createDynamicTableSink(Context context) {
        return null;
    }

    public DynamicTableSource createDynamicTableSource(Context context) {
        return null;
    }

    public String factoryIdentifier() {
        return FACTORY_IDENTIFIER;
    }

    public Set<ConfigOption<?>> requiredOptions() {
        return null;
    }

    public Set<ConfigOption<?>> optionalOptions() {
        return null;
    }
}
