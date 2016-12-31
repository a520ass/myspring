package com.hf.thread;

/**
 * Created by 520 on 2016/12/29.
 * 1）wait()、notify()和notifyAll()方法是本地方法，并且为final方法，无法被重写。
 　2）调用某个对象的wait()方法能让当前线程阻塞，并且当前线程必须拥有此对象的monitor（即锁，或者叫管程）
 　3）调用某个对象的notify()方法能够唤醒一个正在等待这个对象的monitor的线程，如果有多个线程都在等待这个对象的monitor，则只能唤醒其中一个线程；
 　4）调用notifyAll()方法能够唤醒所有正在等待这个对象的monitor的线程；
 */
public class WaitNotify {

    public static void main(String args[]){
        final byte a[] = {0};//以该对象为共享资源
        new Thread(new NumberPrint((1),a),"1").start();
        new Thread(new NumberPrint((2),a),"2").start();
    }
}
class NumberPrint implements Runnable{
    private int number;
    public byte res[];
    public static int count = 5;
    public NumberPrint(int number, byte a[]){
        this.number = number;
        res = a;
    }

    @Override
    public void run() {
        synchronized (res){
            while (count-->0){
                res.notify();//唤醒等待res资源的线程，把锁交给线程（该同步锁执行完毕自动释放锁）
                System.out.println(" "+number);
                try {
                    res.wait();//释放CPU控制权，释放res的锁，本线程阻塞，等待被唤醒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------线程"+Thread.currentThread().getName()+"获得锁，wait()后的代码继续运行："+number);
            }//end while
            return;
        }//end synchronized
    }
}
