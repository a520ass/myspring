package org.hf.config.zookeeper;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

public class CuratorDistrLockTest {
	
	/** Zookeeper info */
    private static final String ZK_ADDRESS = "10.10.3.118:2181,10.10.3.118:2182,10.10.3.118:2183";
    private static final String ZK_LOCK_PATH = "/zktest";

    public static void main(String[] args) throws InterruptedException {
        // 1.Connect to zk
        final CuratorFramework client = CuratorFrameworkFactory.newClient(
                ZK_ADDRESS,
                new RetryNTimes(10, 5000)
        );
        client.start();
        System.out.println("zk client start successfully!");

        Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				doWithLock(client);
			}
		}, "t1");
        Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				doWithLock(client);
			}
		}, "t2");

        t1.start();
        t2.start();
    }

    private static void doWithLock(CuratorFramework client) {
        InterProcessMutex lock = new InterProcessMutex(client, ZK_LOCK_PATH);
        try {
            if (lock.acquire(10 * 1000, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " hold lock");
                Thread.sleep(5000L);
                System.out.println(Thread.currentThread().getName() + " release lock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
