package com.quartz.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@EnableAutoConfiguration
public class QuartzSchedulerConfig {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private AutowiringSpringBeanJobFactory jobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);        
        factory.setQuartzProperties(quartzProperties());
        factory.setJobFactory(jobFactory);
        factory.setStartupDelay(20);
        return factory;
    }
    
    @Bean
    @ConfigurationProperties(prefix = "spring.quartz.properties")
    public Properties quartzProperties() {
        return new Properties();
    }
 
    
}
