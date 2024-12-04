package com.zj.onelake.sink;

import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.operators.coordination.OperatorCoordinator;
import org.apache.flink.streaming.api.operators.*;
import org.apache.flink.table.data.RowData;
import org.apache.iceberg.flink.TableLoader;

import java.util.UUID;

/**
 * @Author geniuszhang
 * @Date 2024/12/4 23:38
 * @Description: TODO
 * @Version 1.0
 */
public class WriterOperatorFactory extends AbstractStreamOperatorFactory<Void>
        implements OneInputStreamOperatorFactory<RowData, Void>, CoordinatedOperatorFactory<Void> {

    private TableLoader tableLoader;

    private UUID uuid;

    public WriterOperatorFactory(TableLoader tableLoader, UUID uuid) {
        this.tableLoader = tableLoader;
        this.uuid = uuid;
    }

    @Override
    public OperatorCoordinator.Provider getCoordinatorProvider(String s, OperatorID operatorID) {
        return null;
    }

    @Override
    public <T extends StreamOperator<Void>> T createStreamOperator(StreamOperatorParameters<Void> streamOperatorParameters) {
        return null;
    }

    @Override
    public Class<? extends StreamOperator> getStreamOperatorClass(ClassLoader classLoader) {
        return null;
    }
}
