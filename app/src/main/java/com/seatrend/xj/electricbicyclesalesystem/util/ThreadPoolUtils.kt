package com.seatrend.xj.electricbicyclesalesystem.util

import java.lang.reflect.Field
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by ly on 2020/3/9 14:05
 *
 *
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
class ThreadPoolUtils {

    //cpu数量
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()

    // 线程池核心线程数
    private var CORE_POOL_SIZE = 2
    // 线程池最大线程数
    private var MAX_POOL_SIZE = 10
    // 额外线程空状态生存时间
    private var KEEP_ALIVE_TIME = 30
    //是否主线程
    private var isMainThread = false

    // 阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程。
    private val workQueue = ArrayBlockingQueue<Runnable>(
        10
    )

    companion object {
        val instance: ThreadPoolUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ThreadPoolUtils()
        }
    }

    // 线程核心数量
    fun setMainThreadSize(size: Int): ThreadPoolUtils? {
        CORE_POOL_SIZE = size
        return instance
    }

    // 线程最大数量
    fun setPoolSize(size: Int): ThreadPoolUtils? {
        MAX_POOL_SIZE = size
        return instance
    }

    // 设置是否是主线程的thread
    fun setMainThread(mainFlag: Boolean): ThreadPoolUtils? {
        isMainThread = mainFlag
        return instance
    }

    // get
    fun getMainThread(): Boolean {
        return isMainThread
    }


    // 线程工厂
    private val threadFactory = object : ThreadFactory {
        private val integer = AtomicInteger()

        override fun newThread(r: Runnable): Thread {
            return Thread(r, "myThreadPool thread:" + integer.getAndIncrement())
        }
    }

    // 线程池
    private var threadPool: ThreadPoolExecutor? = null


    init {
        threadPool = ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME.toLong(),
            TimeUnit.SECONDS,
            workQueue,
            threadFactory
        )
    }

    /**
     * 从线程池中抽取线程，执行指定的Runnable对象
     *
     * @param runnable
     */
    fun execute(runnable: Runnable) {
        threadPool!!.execute(runnable)
    }

    /**
     * 判断当前线程池是否繁忙
     * @return
     */
    fun isBusy(): Boolean {
        return threadPool!!.activeCount >= threadPool!!.corePoolSize
    }
}
