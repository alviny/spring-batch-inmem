package com.example;


import com.example.batch.BatchConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;


/**
 * Exclude the auto-configuration of data sources and transaction managers.
 * Also limit scanning for configuration classes to the main Batch configuration
 * and its package.
 *
 * @see BatchConfig
 */
@SpringBootApplication(scanBasePackageClasses = BatchConfig.class,
                       exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }
}
