package com.example.batch;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;


/**
 * This component is found because it's in the same package as the {@link BatchConfig}.
 * The Spring Batch auto-config discovers it and uses it to configure the Batch
 * subsystem. I'm not sure how to manually create/register this bean.
 *
 * @see #initialize() For the actual in-memory Job Repository
 */
@Component
public class CustomBatchConfigurer implements BatchConfigurer {

    private static final Log LOG = LogFactory.getLog(CustomBatchConfigurer.class);

    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;


    @Override
    public JobRepository getJobRepository() throws Exception {

        return jobRepository;
    }


    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {

        return transactionManager;
    }


    @Override
    public JobLauncher getJobLauncher() throws Exception {

        return jobLauncher;
    }


    @Override
    public JobExplorer getJobExplorer() throws Exception {

        return jobExplorer;
    }


    /**
     * Much of this code was lifted from the
     * {@link org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer}.
     * The difference is that we don't care about the presence of a DataSource,
     * and we're only interested in an in-memory Job Repository, so we can just
     * create the right beans without anything too fancy.
     *
     * @throws Exception
     */
    @PostConstruct
    public void initialize() throws Exception {

        LOG.info("Configuring custom batch environment");

        this.transactionManager = new ResourcelessTransactionManager();

        MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(this.transactionManager);
        jobRepositoryFactory.afterPropertiesSet();
        this.jobRepository = jobRepositoryFactory.getObject();

        MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
        jobExplorerFactory.afterPropertiesSet();
        this.jobExplorer = jobExplorerFactory.getObject();

        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        launcher.afterPropertiesSet();
        this.jobLauncher = launcher;
    }
}
