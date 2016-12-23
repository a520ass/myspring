package com.hf.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 阻塞队列
 * 
 * @author 520
 *
 */
public class BlockingQueueTest {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		// 创建容纳10个整数的队列。
		final BlockingQueue<Integer> queue = new LinkedBlockingDeque<Integer>(
				10);
		// 队列末尾的标志
		final Integer endInt = new Integer(0);

		// 写队列的线程。
		Runnable setter = new Runnable() {
			public void run() {
				try {
					for (int i = 1; i < 10; i++) {
						queue.put(new Integer(i));

						System.out.println(Thread.currentThread().getName()
								+ ": 向队列写入数字" + i);

					}
					queue.put(new Integer(0));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		exec.submit(setter);

		// 两个读队列的线程
		for (int i = 0; i < 2; i++) {
			Runnable getter = new Runnable() {
				public void run() {
					while (true) {
						try {
							Thread.sleep(500);
							Integer num = queue.take();
							if (num.equals(endInt)) {
								queue.put(new Integer(0));
								break;
							}
							System.out.println(Thread.currentThread().getName()
									+ ": 向队列读出数字" + num.intValue());

						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			exec.submit(getter);
		}
		exec.shutdown();

	}
}
