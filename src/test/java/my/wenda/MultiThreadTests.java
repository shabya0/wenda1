package my.wenda;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

//多线程范例
class MyThread extends Thread{
    private int tid;

    public MyThread(int tid){
        this.tid = tid;
    }

    @Override
    public void run(){
        try{
            for(int i=0;i <5; ++i){
                Thread.sleep(1000);
                System.out.println(String.format("%d,%d",tid,i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class  Consumer implements  Runnable{
    private BlockingQueue<String> q;
    public Consumer(BlockingQueue<String> q){
        this.q = q;
    }

    public void run(){
        try{
            while(true){
                System.out.println(Thread.currentThread().getName()+" "+q.take());
            }
        }catch (Exception e){
            System.out.println("Consumer_error: ");
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable{
    private  BlockingQueue<String> q;
    public Producer(BlockingQueue<String> q){
        this.q = q;
    }

    public void run(){
        try{
            for(int i = 0; i < 20; ++i){
                Thread.sleep(1000);
                q.put(String.valueOf(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

public class MultiThreadTests {

    private static Object obj =new Object();

    public static void testSynchronized1(){
        synchronized (obj){
            try{
                for(int j = 0; j < 10; ++j){
                    Thread.sleep(1000);
                    System.out.println(String.format("method_3: %d",j));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void testSynchronized2(){
        synchronized (obj){
            try{
                for(int j = 0; j < 10; ++j){
                    Thread.sleep(1000);
                    System.out.println(String.format("method_4: %d",j));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //测试线程锁
    public static void testSynchronized(){
        for(int i=0; i < 10; ++i){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testSynchronized1();
                    testSynchronized2();
                }
            }).start();
        }
    }

    public static void testThread(){
        for(int i=0; i<10; ++i){
//            new MyThread(i).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int j=0;j<10; ++j){
                            Thread.sleep(1000);
                            System.out.println(String.format("method_2,%d",j));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void testBlockingQueue(){
            BlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
            new Thread(new Producer(q)).start();
            new Thread(new Consumer(q), "Consumer1").start();
            new Thread(new Consumer(q), "Consumer2").start();
    }

    private static ThreadLocal<Integer> threadLocalUserIds = new ThreadLocal<>();
    private static int userId;

    public static void testThreadLocal(){
        for(int i=0; i < 10; ++i){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        threadLocalUserIds.set(finalI);
                        Thread.sleep(1000);
                        System.out.println("ThreadLocal: "+ threadLocalUserIds.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void testExecutor(){
        ExecutorService service = Executors.newFixedThreadPool(2);//2为可并行线程数
        service.submit(new Runnable(){
            public void run(){
                for(int i = 0; i < 10; ++i){
                    try{
                        Thread.sleep(1000);
                        System.out.println("Executor1: "+i);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10; ++i){
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor2: "+i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        service.shutdown();     //关闭已执行完闭的任务，并且不再接受新的submit
    }

    AtomicInteger atoin = new AtomicInteger(0); //原子性变量，多线程用

    public static void main(String[] args) {
//        testThread();
//        testSynchronized();
//        testBlockingQueue();
        testThreadLocal();
    }
}
