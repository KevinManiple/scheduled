package com.kevin.demo.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kevin.demo.common.scheduled.ScheduledSupport;

public class MyThread extends Thread {
    
    private static final Logger LOGGER = LogManager.getLogger(MyThread.class);
    
    private String key;
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public MyThread(String key) {
        this.key = key;
    }
    
    @Override
    public void run() {
        int before = ScheduledSupport.getScheduledPoolSize();
        /* 将执行完的线程从集合中清除掉 */
        ScheduledSupport.removeScheduled(key);
        int end = ScheduledSupport.getScheduledPoolSize();
        LOGGER.info(String.format("%s\tKey: %s\t[正在运行 运行前池大小:%s\t运行后池大小:%s]", Thread.currentThread().getName(), key,
                before, end));
    }
}
