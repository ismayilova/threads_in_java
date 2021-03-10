package com.ismayilova.thread_pool;

import com.ismayilova.ThreadColors;
import com.ismayilova.thread_pool.config.Task;
import com.ismayilova.thread_pool.config.ThreadPoolConfig;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Main {

public static void main(String[] args) {
//    1) Vizivayem 1 raz dla polucheniya chisla zayavok v main thread
    int count  = 3300;
    int q = count%1000;
    int p= count/1000;
    int intCount = p*1000;
    System.out.println(ThreadColors.WHITE +Thread.currentThread().getName() + "  count = "+count);


    //split count by 1000   ---  o, 1000 1000-2000 2000-3000. ...


    List<String> stringList =createList(count);
    List<List<String>> splitted = new ArrayList<>();

    for(int i = 0;i<(count/1000)*1000;i+=1000){

     splitted.add(stringList.subList(i,i+1000));
    }

    splitted.add(stringList.subList(intCount,intCount+q));




//    splitted.stream().forEach(System.out::println);


    ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(3);
    ExecutorService executorService = threadPoolConfig.getExecutorService();


    splitted.stream().parallel().forEach(list -> {
        executorService.execute(new Task(list,ThreadColors.CYAN));


    });

    executorService.shutdown();

}


public static List<String> createList(int n){

    List<String> list = new ArrayList<>();
    for(int i=0;i<n;i++){
        list.add("String ["+i+"]");
    }
  return list;

}

 }


