package com.zj.onelake;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.table.catalog.Catalog;
import org.apache.iceberg.CatalogProperties;
import org.apache.iceberg.catalog.Namespace;
import org.apache.iceberg.flink.CatalogLoader;
import org.apache.iceberg.flink.FlinkCatalogFactory;
import org.apache.iceberg.relocated.com.google.common.base.Preconditions;
import org.apache.iceberg.util.PropertyUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Author geniuszhang
 * @Date 2024/11/30 16:33
 * @Description: TODO
 * @Version 1.0
 */
public class OneLakeCatalogFactory extends FlinkCatalogFactory {

    public static final String FACTORY_IDENTIFIER = "onelake";

    @Override
    public String factoryIdentifier() {
        return FACTORY_IDENTIFIER;
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        return Collections.emptySet();
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        // TODO: add required and optional
        return Collections.emptySet();
    }

    @Override
    public Catalog createCatalog(Context context) {
        return createCatalog(context.getName(), context.getOptions());
    }

    public Catalog createCatalog(String name, Map<String, String> properties) {
        CatalogLoader catalogLoader = createCatalogLoader(name, properties, clusterHadoopConf());
        String defaultDatabase = properties.getOrDefault(DEFAULT_DATABASE, DEFAULT_DATABASE_NAME);

        Namespace baseNamespace = Namespace.empty();
        if (properties.containsKey(BASE_NAMESPACE)) {
            baseNamespace = Namespace.of(properties.get(BASE_NAMESPACE).split("\\."));
        }

        boolean cacheEnabled =
                PropertyUtil.propertyAsBoolean(
                        properties,
                        CatalogProperties.CACHE_ENABLED,
                        CatalogProperties.CACHE_ENABLED_DEFAULT);

        long cacheExpirationIntervalMs =
                PropertyUtil.propertyAsLong(
                        properties,
                        CatalogProperties.CACHE_EXPIRATION_INTERVAL_MS,
                        CatalogProperties.CACHE_EXPIRATION_INTERVAL_MS_OFF);
        Preconditions.checkArgument(
                cacheExpirationIntervalMs != 0,
                "%s is not allowed to be 0.",
                CatalogProperties.CACHE_EXPIRATION_INTERVAL_MS);

        return new OneLakeCatalog(
                name,
                defaultDatabase,
                baseNamespace,
                catalogLoader,
                cacheEnabled,
                cacheExpirationIntervalMs);
    }
}
