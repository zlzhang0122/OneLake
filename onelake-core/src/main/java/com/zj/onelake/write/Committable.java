package com.zj.onelake.write;

import com.zj.onelake.index.IndexFile;
import org.apache.flink.table.data.binary.BinaryRowData;

import java.util.List;

/**
 * @Author geniuszhang
 * @Date 2024/12/1 21:31
 * @Description: TODO
 * @Version 1.0
 */
public class Committable {
    private final long commitId;

    private final BinaryRowData partition;

    private final int bucket;

    // Newly created data files.
    protected final List<DataFile> newDataFiles;

    // Files changed before.
    protected final List<DataFile> compactBefore;

    // Files after compaction.
    protected final List<DataFile> compactAfter;

    protected final List<DataFile> changelogFiles;

    protected final List<IndexFile> indexFiles;

    public Committable(
            long commitId,
            BinaryRowData partition,
            int bucket,
            List<DataFile> newDataFiles,
            List<DataFile> compactBefore,
            List<DataFile> compactAfter,
            List<DataFile> changelogFiles,
            List<IndexFile> indexFiles) {
        this.commitId = commitId;
        this.partition = partition;
        this.bucket = bucket;
        this.newDataFiles = newDataFiles;
        this.compactBefore = compactBefore;
        this.compactAfter = compactAfter;
        this.changelogFiles = changelogFiles;
        this.indexFiles = indexFiles;
    }
}
