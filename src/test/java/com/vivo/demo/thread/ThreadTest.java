package com.vivo.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadTest {
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CallableTest t1 = new CallableTest();
        CallableTest t2 = new CallableTest();
        FutureTask<String> f1 = new FutureTask<String>(t1);
        FutureTask<String> f2 = new FutureTask<String>(t2);
        new Thread(f1).start();
        new Thread(f2).start();
        System.out.println(f1.get());
        System.out.println(f2.get());
    }
}

class CallableTest implements Callable<String> {
    
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + ": 我执行了");
        return Thread.currentThread().getName();
    }
}