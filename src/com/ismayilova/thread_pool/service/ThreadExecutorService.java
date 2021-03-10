package com.ismayilova.thread_pool.service;

import com.ismayilova.thread_pool.config.ThreadPoolConfig;

import java.util.concurrent.ExecutorService;
//NOT git USED
public class ThreadExecutorService {

    public ExecutorService  get(){
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();

        return threadPoolConfig.getExecutorService();
    }






}
