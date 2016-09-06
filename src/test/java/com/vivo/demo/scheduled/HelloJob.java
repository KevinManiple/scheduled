package com.vivo.demo.scheduled;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kevin.demo.controller.IndexController;

public class HelloJob implements Job {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Hello World! - " + new Date());
    }
}
