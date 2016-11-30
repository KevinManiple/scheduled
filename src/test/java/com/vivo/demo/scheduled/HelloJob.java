package com.vivo.demo.scheduled;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {
    
    private static final Logger LOGGER = LogManager.getLogger(HelloJob.class);
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Hello World! - " + new Date());
    }
}
