package com.kevin.demo.common.scheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * <p>
 * Description: 任务调度池
 * </p>
 * 
 * @author: Kai.Zhao
 */
public enum ScheduledProcessPool {
    INSTANCE;
    
    private Map<String, ScheduledFuture<?>> scheduledPool;
    
    /** 调度池初始容量 */
    private int initialCapacity = 50;
    
    private ScheduledProcessPool() {
        scheduledPool = new ConcurrentHashMap<String, ScheduledFuture<?>>(initialCapacity);
    }
    
    /**
     * <p>
     * Description: 获取任务调度池
     * </p>
     * 
     * @return
     */
    public Map<String, ScheduledFuture<?>> getPool() {
        return scheduledPool;
    }
}
