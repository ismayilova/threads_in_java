package com.ismayilova.thread_pool.config;

import com.ismayilova.ThreadColors;
import javafx.util.Pair;

public class Task2 implements  Runnable{
    private int offset;
    private int limit;

    public Task2(int offset ,int limit){
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public void run() {
        try{
            System.out.println( ThreadColors.RED+Thread.currentThread().getName() + "======="+ ThreadColors.GREEN + "Starting from "+ offset + ", up to " + limit );
            Thread.sleep(3000);

        }catch (InterruptedException e) {
//           e.printStackTrace();
            System.out.println(ThreadColors.RED + "Opps something went wrong ");
        }

    }
}
