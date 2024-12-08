package com.zj.onelake.index;

/**
 * @Author geniuszhang
 * @Date 2024/12/5 23:33
 * @Description: TODO
 * @Version 1.0
 */
public class IndexFile {
    private final IndexTypeEnum indexTypeEnum;

    private final String fileName;

    private final long fileSizeInBytes;

    private final long rowCount;

    public IndexFile(IndexTypeEnum indexTypeEnum, String fileName, long fileSizeInBytes, long rowCount) {
        this.indexTypeEnum = indexTypeEnum;
        this.fileName = fileName;
        this.fileSizeInBytes = fileSizeInBytes;
        this.rowCount = rowCount;
    }

    public IndexTypeEnum getIndexTypeEnum() {
        return indexTypeEnum;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public long getRowCount() {
        return rowCount;
    }
}
