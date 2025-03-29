package com.threedollar.config.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TransactionManagerConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        manager.setDataSource(dataSource);
        return manager;

    }

}
