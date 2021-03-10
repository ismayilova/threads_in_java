package com.ismayilova.thread_pool.config;

import com.ismayilova.ThreadColors;

import java.util.List;

public class Task implements Runnable {
  private   List<String> list ;
    String color ;

    public Task(List<String>list,String color){
        this.list = list;
        this.color = color;
    }


    @Override
    public void run() {
       int size  = list.size();

       try {
           //make request  new signature new isb request
           System.out.println( color + "In new thread " + Thread.currentThread().getName());
           System.out.println("Started handling list========================== " + ThreadColors.MAGENTA + list);
           System.out.println("=================================================================================");
           Thread.sleep(5000);
       } catch (InterruptedException e) {
//           e.printStackTrace();
           System.out.println(ThreadColors.RED + "Opps something went wrong ");
       }

    }
}
