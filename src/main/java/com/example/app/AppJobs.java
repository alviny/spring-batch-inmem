package com.example.app;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * In a real Batch application, the Jobs would be configured here, or in
 * multiple classes like this one. Notice that any additional configuration
 * that the jobs need, like data sources, are configured in yet another config
 * class, which is imported here (remember that we limited component scanning).
 *
 * @see AppConfig
 */
@Configuration
@Import(AppConfig.class)
public class AppJobs {

}
