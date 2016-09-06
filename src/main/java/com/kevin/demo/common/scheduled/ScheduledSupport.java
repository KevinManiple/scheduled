package com.kevin.demo.common.scheduled;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Description: 任务调度支持类 <br>
 * 当调度执行完或者中断都需要把该任务从任务调度池中删除掉, 这样是为了避免池中垃圾对象积累。 <br>
 * <br>
 * <code>
 * public void run() {<br>
 * <pre>// do something...</pre>
 * <pre>ScheduleProcessSupport.remove(key);</pre>
 * }
 * </code>
 * </p>
 * 
 * @author: Kai.Zhao
 */
public class ScheduledSupport {
    
    private static Map<String, ScheduledFuture<?>> scheduleFutureMap = ScheduledProcessPool.INSTANCE.getPool();
    
    private static ScheduledExecutorService scheduledService = ScheduledThreadPool.INSTANCE.getPool();
    
    /**
     * <p>
     * Description: 获取任务调度
     * </p>
     * 
     * @param key
     *            主键
     * @return
     */
    private static ScheduledFuture<?> getScheduledFutrue(String key) {
        ScheduledFuture<?> future = scheduleFutureMap.get(key);
        removeScheduled(key);
        return future;
    }
    
    /**
     * <p>
     * Description: 取消任务调度
     * </p>
     * 
     * @param key
     */
    private static void cancelScheduled(String key) {
        ScheduledFuture<?> future = getScheduledFutrue(key);
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
    }
    
    /**
     * <p>
     * Description: 创建延迟任务
     * </p>
     * 
     * @param key
     *            主键
     * @param thread
     *            线程
     * @param delay
     *            延迟执行时间
     * @param timeUnit
     *            延迟参数的时间单位
     */
    public static void schedule(String key, Thread thread, long delay, TimeUnit timeUnit) {
        cancelScheduled(key);
        scheduleFutureMap.put(key, scheduledService.schedule(thread, delay, timeUnit));
    }
    
    /**
     * <p>
     * Description: 创建延迟任务
     * </p>
     * 
     * @param key
     *            主键
     * @param thread
     *            线程
     * @param delay
     *            延迟执行时间(单位：秒)
     */
    public static void scheduleDelaySeconds(String key, Thread thread, long delay) {
        schedule(key, thread, delay, TimeUnit.SECONDS);
    }
    
    /**
     * <p>
     * Description: 移除任务调度
     * </p>
     * 
     * @param key
     *            主键
     * @return
     */
    public static void removeScheduled(String key) {
        if (scheduleFutureMap.containsKey(key)) {
            Iterator<String> it = scheduleFutureMap.keySet().iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(key)) {
                    it.remove();
                    break;
                }
            }
        }
    }
    
    /**
     * <p>
     * Description: 获取调度池容量
     * </p>
     * 
     * @return
     */
    public static int getScheduledPoolSize() {
        return scheduleFutureMap.size();
    }
}
