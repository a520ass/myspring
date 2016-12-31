package com.hf.thread.lockcondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock Condition实现阻塞队列
 * Created by 520 on 2016/12/31.
 */
public class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[2]; // 阻塞队列
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        System.out.println("进入put");
        lock.lock();
        System.out.println("put lock 锁住");
        try {
            while (count == items.length) { // 如果队列满了，notFull就一直等待
                System.out.println("put notFull 等待");
                notFull.await(); // 调用await的意思取反，及not notFull -> Full
            }
            items[putptr] = x; // 终于可以插入队列
            if (++putptr == items.length)
                putptr = 0; // 如果下标到达数组边界，循环下标置为0
            ++count;
            System.out.println("put notEmpty 唤醒");
            notEmpty.signal(); // 唤醒notEmpty
        } finally {
            System.out.println("put lock 解锁");
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        System.out.println("take lock 锁住");
        try {
            while (count == 0) {
                System.out.println("take notEmpty 等待");
                notEmpty.await();
            }
            Object x = items[takeptr];
            if (++takeptr == items.length)
                takeptr = 0;
            --count;
            System.out.println("take notFull 唤醒");
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
            System.out.println("take lock 解锁");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final BoundedBuffer bb = new BoundedBuffer();
        System.out.println(Thread.currentThread()+","+bb);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread()+","+bb);
                    bb.put("xx");
                    bb.put("yy");
                    bb.put("zz");
                    bb.put("zz");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        bb.take();
    }
}
