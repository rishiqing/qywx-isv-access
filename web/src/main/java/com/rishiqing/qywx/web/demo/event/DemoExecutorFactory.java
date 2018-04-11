package com.rishiqing.qywx.web.demo.event;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DemoExecutorFactory {
    public Executor getFetchDeptAndStaffExecutor(){
        //  默认建立10个线程的线程池
        return Executors.newFixedThreadPool(10);
    }
}
