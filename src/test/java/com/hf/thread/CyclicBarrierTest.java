package com.hf.thread;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

	public static void main(String[] args) {
		// 创建需要同步3个线程的同步辅助对象
		CyclicBarrier barrier = new CyclicBarrier(3);
		// 创建容量为3的固定线程池,wait
		ExecutorService exec = Executors.newFixedThreadPool(3);

		// 向线程池提交三个任务
		
		exec.submit(new Runner(barrier, 1, new int[] { 1, 12, 3 }));
		exec.submit(new Runner(barrier, 2, new int[] { 6, 4, 6 }));
		exec.submit(new Runner(barrier, 3, new int[] { 4, 8, 12 }));

		// 销毁线程池
		exec.shutdown();
	}
}

class Runner implements Runnable {
	private int no = 0;// 线程序号
	private int[] times = null;// 到达每个集合点需要的时间，单位为秒
	private CyclicBarrier barrier = null;// 同步辅助对象

	public Runner(CyclicBarrier barrier, int no, int[] times) {
		this.times = times;
		this.no = no;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(times[0] * 1000);
			System.out.println(new Date() + "Thread_" + no
					+ " Arrives At Point 1");
			barrier.await();

			Thread.sleep(times[1] * 1000);
			System.out.println(new Date() + "Thread_" + no
					+ " Arrives At Point 2");
			barrier.await();

			Thread.sleep(times[2] * 1000);
			System.out.println(new Date() + "Thread_" + no
					+ " Arrives At Point 3");
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}
