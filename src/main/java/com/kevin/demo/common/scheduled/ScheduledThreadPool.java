package com.kevin.demo.common.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * <p>
 * Description: 任务线程池
 * </p>
 * 
 * @author: Kai.Zhao
 */
public enum ScheduledThreadPool {
    INSTANCE;
    
    /** 线程池初始容量 */
    private int corePoolSize = 50;
    
    private ScheduledExecutorService exec;
    
    private ScheduledThreadPool() {
        exec = Executors.newScheduledThreadPool(corePoolSize, new ThreadFactoryBuilder().setNamePrefix("VIVO-H5-Thread")
                .setDaemon(false).setPriority(Thread.MAX_PRIORITY).build());
    }
    
    /**
     * <p>
     * Description: 获取任务线程池
     * </p>
     * 
     * @return
     */
    public ScheduledExecutorService getPool() {
        return exec;
    }
}
