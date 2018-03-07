package com.rishiqing.qywx.service.event.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncFetchCorpAllExecutorFactory {
    public Executor getFetchCorpAllExecutor(){
        //  默认建立10个线程的线程池
        return Executors.newFixedThreadPool(10);
    }
}
