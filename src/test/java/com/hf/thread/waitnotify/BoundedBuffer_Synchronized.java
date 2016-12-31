package com.hf.thread.waitnotify;

/**
 * 使用wait notify实现阻塞队列 FIFO
 * Created by 520 on 2016/12/29.
 */
public class BoundedBuffer_Synchronized {
    private Object[] items = new Object[2];
    private Object notEmpty = new Object();
    private Object notFull = new Object();
    int count,putidx,takeidx;

    public void put(Object obj) throws InterruptedException {
        synchronized(notFull){
            while (count==items.length){//当count等于总长度的时候，队列已经满了 阻塞put方法
                notFull.wait();
            }
        }
        items[putidx]=obj;
        if (++putidx==items.length){
            putidx=0;   // 如果下标到达数组边界，循环下标置为0
        }
        count++;
        synchronized (notEmpty){
            notEmpty.notify();// 唤醒notEmpty
        }
    }

    public Object take() throws InterruptedException{
        synchronized(notEmpty){
            while(count == 0){ // 啥也没有呢 取啥 直接阻塞
                notEmpty.wait();
            }
        }
        Object x = items[takeidx];
        System.out.println("取第"+takeidx+"个元素"+x);
        if(++takeidx == items.length){
            takeidx = 0;    // 如果下标到达数组边界，循环下标置为0
        }
        count --;
        synchronized (notFull) {
            notFull.notify();
        }
        return x;
    }

    public static void main(String[] args) throws InterruptedException {
        final BoundedBuffer_Synchronized bb = new BoundedBuffer_Synchronized();
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
                    bb.put("zz");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        bb.take();
        bb.take();
    }
}
