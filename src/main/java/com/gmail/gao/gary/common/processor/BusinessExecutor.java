package com.gmail.gao.gary.common.processor;

import java.util.concurrent.*;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 11:25 PM
 */
public class BusinessExecutor {
    private static BusinessExecutor instance = new BusinessExecutor();

    private static final String THREAD_GROUP_NAME = "BusinessExecutorGroup";

    private static final String THREAD_POOL_NAME = "BusinessExecutor";

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int QUEUE_CAPACITY = 100;


    private ThreadGroup threadGroup;

    private ThreadFactory threadFactory;

    private ExecutorService executorService;

    /**
     * singleton instance
     * @return
     */
    public static BusinessExecutor getInstance() {
        return instance;
    }

    private BusinessExecutor() {
        this.threadGroup = new ThreadGroup(THREAD_GROUP_NAME);
        this.threadFactory = new BusinessThreadFactory(THREAD_POOL_NAME, threadGroup);
        this.executorService = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY),
                this.threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * use thread pool to execute command
     * @param command
     */
    public void execute(Runnable command) {
        executorService.execute(command);
    }

    /**
     * using thread pool to execute callable task
     * @param task
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }

    /**
     * current thread is in this thread pool
     * @return
     */
    public boolean isExecutingByCurrentExecutor() {
        return Thread.currentThread().getThreadGroup().equals(this.threadGroup);
    }

}
