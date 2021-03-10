package com.ismayilova.thread_pool.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class ThreadPoolConfig {

    private ExecutorService executorService;


    public ThreadPoolConfig(){
        this.executorService = Executors.newFixedThreadPool(5);
    }


    public ThreadPoolConfig(int n){
        this.executorService = Executors.newFixedThreadPool(n);
    }

    public ThreadPoolConfig(ExecutorService service){
        this.executorService = service;
    }

    public  ExecutorService getExecutorService(){
        return this.executorService;
    }

}
