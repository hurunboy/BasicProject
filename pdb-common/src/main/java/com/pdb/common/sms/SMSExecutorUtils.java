package com.pdb.common.sms;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池操作处理类
 */
public class SMSExecutorUtils {

    private static final Logger logger = LoggerFactory.getLogger(SMSExecutorUtils.class);

    /**
     * 线程池操作处理
     */
    private static final ExecutorService SINGLE_THREAD_POOL = new ThreadPoolExecutor(100, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), new ThreadFactoryBuilder().build(), new ThreadPoolExecutor.AbortPolicy());

    /**
     * 执行线程
     * @param runnable
     */
    public static final void executeTask(TeleMessageSendThread runnable){
        SINGLE_THREAD_POOL.execute(runnable);
    }

    /**
     * 关闭线程池
     */
    public static final void shutdown(){
        SINGLE_THREAD_POOL.shutdown();
    }

    /**
     * 线程池信息
     */
    public static void poolInfo(){
        logger.info("【MatchOrderDB 线程池任务】线程池中线程数：" + ((ThreadPoolExecutor)SINGLE_THREAD_POOL).getPoolSize());
        logger.info("【MatchOrderDB 线程池任务】队列中等待执行的任务数：" + ((ThreadPoolExecutor)SINGLE_THREAD_POOL).getQueue().size());
        logger.info("【MatchOrderDB 线程池任务】已执行完任务数：" + ((ThreadPoolExecutor)SINGLE_THREAD_POOL).getCompletedTaskCount());
    }
}
