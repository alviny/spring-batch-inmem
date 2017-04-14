package com.example.batch;


import com.example.app.AppJobs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


/**
 * <p>
 * Enable modular batch configuration. I don't know if this is strictly
 * necessary, but without it, the Spring Batch auto-config defaults to a
 * {@link org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration},
 * which assumes the presence of a single {@code DataSource} bean. I'm sure
 * there's another way around it, but the solution didn't present itself in
 * the hour or so that I looked at it (it probably would require fully manual
 * configuration of the Batch subsystem).
 * </p>
 * <p>
 * Note that this is the only config file that's picked up by Spring Boot's
 * scanning. Actually, to be precise, any config file in this same package would
 * be picked up. If there are other configuration files that should be processed
 * (not Job configuration classes), then those should either be imported here,
 * or should be added to the {@link com.example.DemoApplication application's}
 * component scanning.
 * </p>
 *
 * @see AppJobs
 * @see CustomBatchConfigurer
 */
@Configuration
@EnableBatchProcessing(modular = true)
public class BatchConfig {

    private static final Logger LOG = LoggerFactory.getLogger(BatchConfig.class);


    /**
     * This is the standard modular Batch configuration. By creating a
     * sub-context around the Job configuration, the Jobs' config doesn't
     * interfere or interact with our {@literal "main"} config.
     *
     * @return
     */
    @Bean
    ApplicationContextFactory appJobs() {

        return new GenericApplicationContextFactory(AppJobs.class);
    }


    @PostConstruct
    public void init() {

        LOG.info("Batch config has been constructed and initialized");
    }
}
