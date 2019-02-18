package com.rishiqing.qywx.service.event.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncDoTrialExecutorFactory {
    public Executor getExecutor(){
        //  默认建立10个线程的线程池
        return Executors.newFixedThreadPool(8);
    }
}
