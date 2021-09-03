package com.ismayilova.thread_pool;

import com.ismayilova.ThreadColors;
import com.ismayilova.thread_pool.config.Task;
import com.ismayilova.thread_pool.config.Task2;
import com.ismayilova.thread_pool.config.ThreadPoolConfig;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Main {

public static void main(String[] args) {
//    1) Vizivayem 1 raz dla polucheniya chisla zayavok v main thread
    int count  = 3330;
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



    /*          int chunkSize = 1;

    * Atomic valu usage
    *  AtomicInteger counter = new AtomicInteger();
    *  List<List<Order>> splited = (List<List<String>>) stringList.stream()//.parallel().
                   .collect(Collectors.groupingBy(order -> counter.getAndDecrement()/chunkSize))
                   .values();
    * */



    ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(3);
    ExecutorService executorService = threadPoolConfig.getExecutorService();


    splitted.stream().parallel().forEach(list -> {
        executorService.execute(new Task(list,ThreadColors.CYAN));
    });

    executorService.shutdown();


    ThreadPoolConfig threadPoolConfig2 =  new ThreadPoolConfig();
    ExecutorService executorService2 = threadPoolConfig2.getExecutorService();

    List<Pair> indexList =  createSplitIndex(count);
    indexList.stream().parallel().forEach( el ->{
                  executorService2.execute(new Task2((int)el.getKey() , (int)el.getValue()));
    });
    executorService2.shutdown();


}


public static List<String> createList(int n){

    List<String> list = new ArrayList<>();
    for(int i=0;i<n;i++){
        list.add("String ["+i+"]");
    }
  return list;

}


public static List<Pair> createSplitIndex(int n){
    int q = n%1000;
    int p= n/1000;
    int intCount = p*1000;
    List<Pair> list = new ArrayList<>();


    for(int i=0;i<p*1000;i+=1000){
//        Pair<Integer,Integer> pair =  new Pair<>(i,i+1000);
        list.add(new Pair<>(i,i+1000));
    }
    list.add(new Pair(intCount,intCount+q));

    return list;
}

 }


