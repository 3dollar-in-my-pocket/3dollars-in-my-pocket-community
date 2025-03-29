package com.threedollar.config.data;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final String MASTER_DB = "master";
    private static final String SLAVE_DB = "slave";


    @Override
    protected Object determineCurrentLookupKey() {
        return (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) ? SLAVE_DB :  MASTER_DB;
    }

    @Bean
    @Primary
    @DependsOn({MASTER_DB, SLAVE_DB})
    public DataSource routingDataSource(
        @Qualifier(MASTER_DB) DataSource masterDataSource,
        @Qualifier(SLAVE_DB) DataSource slaveDataSource) {

        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> datasourceMap = new HashMap<>() {
            {
                put(MASTER_DB, masterDataSource);
                put(SLAVE_DB, slaveDataSource);
            }
        };

        routingDataSource.setTargetDataSources(datasourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource.routingDataSource(masterDataSource, slaveDataSource);

    }


}
