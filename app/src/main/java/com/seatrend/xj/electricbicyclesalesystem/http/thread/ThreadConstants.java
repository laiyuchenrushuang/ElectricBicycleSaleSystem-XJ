package com.seatrend.xj.electricbicyclesalesystem.http.thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程常量池
 *
 * Created by ly on 2020/3/13 9:34
 */
public interface ThreadConstants {


    //参数初始化
    int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));;//核心线程池大小
    int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;//最大线程池队列大小
    int KEEP_ALIVE = 1;//保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间。
    TimeUnit SECONDS = TimeUnit.SECONDS;
    TimeUnit MINUTES = TimeUnit.MINUTES;
    TimeUnit HOURS = TimeUnit.HOURS;
    TimeUnit DAYS = TimeUnit.DAYS;
}
