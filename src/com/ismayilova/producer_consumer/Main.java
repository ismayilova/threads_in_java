package com.ismayilova.producer_consumer;

import com.ismayilova.ThreadColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();
        MyProducer myProducer = new MyProducer(buffer, ThreadColors.RED,bufferLock);
        MyConsumer myConsumer1 = new MyConsumer(buffer,ThreadColors.YELLOW,bufferLock);
        MyConsumer myConsumer2 = new MyConsumer(buffer,ThreadColors.CYAN,bufferLock);

       //Thread Pool
        ExecutorService executorService  = Executors.newFixedThreadPool(5);

        executorService.execute(myProducer);
        executorService.execute(myConsumer1);
        executorService.execute(myConsumer2);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColors.WHITE +"Iam in callable class");
                return "Callable Result ";
            }
        });

        try {
            System.out.println(future.get());
        }catch (ExecutionException | InterruptedException ex){
            System.out.println("WRONG EX");
        }
        executorService.shutdown();

//
//        new Thread(myProducer).start();
//        new Thread(myConsumer1).start();
//        new Thread(myConsumer2).start();

    }
}



class MyProducer implements Runnable{
    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    public MyProducer(List<String> buffer , String color,ReentrantLock bufferLock){
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        Random random = new Random();
        String[] nums = {"1","2","3","4","5"};

        for(String num:nums){

            try{
                System.out.println(color + "Adding ... " + num);
                  bufferLock.lock();
                  try{
                      buffer.add(num);
                  }finally {
                      bufferLock.unlock();
                  }
                  Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {
                System.out.println("Producer was interrupted!!!!!");
            }

        }
        System.out.println(color + "Adding EOF and exiting...");
        bufferLock.lock();
          try {
              buffer.add("EOF");
          }finally {
              bufferLock.unlock();
          }

    }
}


class MyConsumer implements  Runnable{
    private List<String> buffer;
    private String  color;
    private ReentrantLock  bufferLock;

    public MyConsumer(List<String> buffer, String color,ReentrantLock bufferLock){
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true){
                if(bufferLock.tryLock()){

                    try {
                        if(buffer.isEmpty()) {
                            //  bufferLock.unlock();
                            continue;
                        }
                        System.out.println(color + "The counter  = "+ counter);
                        counter=0;
                        if(buffer.get(0).equals("EOF")){
                            System.out.println(color + "Exiting ....");
                            // bufferLock.unlock();
                            break;
                        }else{
                            System.out.println(color + "Removed " + buffer.remove(0));
                        }
                    }finally {
                        bufferLock.unlock();
                    }
                }else {
                    counter+=1;
                }




        }
    }
}