package com.zj.onelake;

import com.zj.onelake.sink.OneLakeTableSInk;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.table.catalog.*;
import org.apache.flink.table.catalog.exceptions.DatabaseAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.TableAlreadyExistException;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.factories.DynamicTableSinkFactory;
import org.apache.flink.table.factories.DynamicTableSourceFactory;
import org.apache.flink.util.Preconditions;
import org.apache.iceberg.catalog.TableIdentifier;
import org.apache.iceberg.exceptions.AlreadyExistsException;
import org.apache.iceberg.flink.TableLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author geniuszhang
 * @Date 2024/10/20 09:10
 * @Description: TODO
 * @Version 1.0
 */
public class OneLakeTableFactory implements DynamicTableSourceFactory, DynamicTableSinkFactory {
    private static final Logger LOG = LoggerFactory.getLogger(OneLakeTableFactory.class);

    public static final String FACTORY_IDENTIFIER = "onelake";

    public static final ConfigOption<String> CATALOG_NAME =
            ConfigOptions.key("catalog-name")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("Catalog name");

    public static final ConfigOption<String> CATALOG_DATABASE =
            ConfigOptions.key("catalog-database")
                    .stringType()
                    .defaultValue(OneLakeCatalogFactory.DEFAULT_DATABASE_NAME)
                    .withDescription("Database name managed in the onelake catalog.");

    public static final ConfigOption<String> CATALOG_TABLE =
            ConfigOptions.key("catalog-table")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "Table name managed in the underlying onelake catalog and database.");

    private OneLakeCatalog catalog;

    public OneLakeTableFactory() {
        this.catalog = null;
    }

    public OneLakeTableFactory(OneLakeCatalog catalog) {
        this.catalog = catalog;
    }

    public DynamicTableSink createDynamicTableSink(Context context) {
        ObjectIdentifier objectIdentifier = context.getObjectIdentifier();
        ResolvedCatalogTable resolvedCatalogTable = context.getCatalogTable();
        Map<String, String> writeProps = new HashMap<>(resolvedCatalogTable.getOptions());

        TableLoader tableLoader;
        if (catalog == null) {
            tableLoader = createTableLoader(catalog, objectIdentifier.toObjectPath());
        } else {
            tableLoader =
                    createTableLoader(
                            resolvedCatalogTable,
                            writeProps,
                            objectIdentifier.getDatabaseName(),
                            objectIdentifier.getObjectName());
        }

        return new OneLakeTableSInk(tableLoader);
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

    private static TableLoader createTableLoader(OneLakeCatalog oneLakeCatalog, ObjectPath objectPath) {
        Preconditions.checkNotNull(oneLakeCatalog, "OneLakeCatalog cannot be null");

        return TableLoader.fromCatalog(
                oneLakeCatalog.getCatalogLoader(), oneLakeCatalog.toIdentifier(objectPath));
    }

    private TableLoader createTableLoader(
            ResolvedCatalogTable resolvedCatalogTable,
            Map<String, String> tableProps,
            String databaseName,
            String tableName) {
        Configuration flinkConf = new Configuration();
        tableProps.forEach(flinkConf::setString);

        String catalogName = flinkConf.getString(CATALOG_NAME);
        Preconditions.checkNotNull(
                catalogName, "Table property '%s' cannot be null", CATALOG_NAME.key());

        String catalogDatabase = flinkConf.getString(CATALOG_DATABASE, databaseName);
        Preconditions.checkNotNull(catalogDatabase, "The onelake database name cannot be null");

        String catalogTable = flinkConf.getString(CATALOG_TABLE, tableName);
        Preconditions.checkNotNull(catalogTable, "The onelake table name cannot be null");

        OneLakeCatalogFactory factory = new OneLakeCatalogFactory();
        OneLakeCatalog catalog = (OneLakeCatalog) factory.createCatalog(catalogName, tableProps);
        ObjectPath objectPath = new ObjectPath(catalogDatabase, catalogTable);

        // Create database if not exists in the external catalog.
        if (!catalog.databaseExists(catalogDatabase)) {
            try {
                catalog.createDatabase(
                        catalogDatabase, new CatalogDatabaseImpl(new HashMap<>(), null), true);
            } catch (DatabaseAlreadyExistException e) {
                throw new AlreadyExistsException(
                        e,
                        "Database %s already exists in the onelake catalog %s.",
                        catalogName,
                        catalogDatabase);
            }
        }

        // Create table if not exists in the external catalog.
        if (!catalog.tableExists(objectPath)) {
            try {
                catalog.createIcebergTable(objectPath, resolvedCatalogTable, true);
                LOG.info("create table:{}", objectPath);
            } catch (TableAlreadyExistException e) {
                throw new AlreadyExistsException(
                        e,
                        "Table %s already exists in the database %s and catalog %s",
                        catalogTable,
                        catalogDatabase,
                        catalogName);
            }
        }
        LOG.info("objectPath:{}", objectPath);

        return TableLoader.fromCatalog(
                catalog.getCatalogLoader(), TableIdentifier.of(catalogDatabase, catalogTable));
    }
}
