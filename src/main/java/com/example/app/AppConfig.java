package com.example.app;


import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Configuration
public class AppConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);


    @Bean
    public DataSource appDataSource() {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:");
        return ds;
    }


    @Bean
    public PlatformTransactionManager appTransactionManager() {

        return new DataSourceTransactionManager(appDataSource());
    }


    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {

        return new TransactionAwareDataSourceProxy(appDataSource());
    }

    @PostConstruct
    public void initialize() {

        LOG.info("Other config has been constructed and initialized");
    }
}
