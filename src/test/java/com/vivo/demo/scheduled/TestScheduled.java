package com.vivo.demo.scheduled;

import java.security.SecureRandom;
import java.util.Random;

import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.kevin.demo.common.utils.HttpConnectionUtil;

public class TestScheduled {
    
    @Test
    public void add() {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        int going = 10;
        while (going-- > 0) {
            sb.append("http://192.168.6.166:8081/scheduled/?");
            sb.append("key=").append(random.nextInt(1000000));
            sb.append("&delay=").append(random.nextInt(10000000));
            System.out.println(sb.toString());
            System.out.println(HttpConnectionUtil.INSTANCE.get(sb.toString(), null));
            sb.setLength(0);
        }
    }
    
    // @Test
    public void testQuartzWithInterval() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever())
                    .build();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
    
    // @Test
    public void testQuartzWithCron() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("dummyJobName", "group1").build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("dummyTriggerName", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).build();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
