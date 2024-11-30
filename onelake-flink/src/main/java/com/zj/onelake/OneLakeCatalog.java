package com.zj.onelake;

import org.apache.iceberg.catalog.Namespace;
import org.apache.iceberg.flink.CatalogLoader;
import org.apache.iceberg.flink.FlinkCatalog;

import java.io.Serializable;

/**
 * @Author geniuszhang
 * @Date 2024/11/30 16:11
 * @Description: TODO
 * @Version 1.0
 */
public class OneLakeCatalog extends FlinkCatalog implements Serializable {

    public OneLakeCatalog(String catalogName, String defaultDatabase, Namespace baseNamespace, CatalogLoader catalogLoader, boolean cacheEnabled, long cacheExpirationIntervalMs) {
        super(catalogName, defaultDatabase, baseNamespace, catalogLoader, cacheEnabled, cacheExpirationIntervalMs);
    }
}
