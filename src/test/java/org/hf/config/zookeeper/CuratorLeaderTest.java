package org.hf.config.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.EnsurePath;

public class CuratorLeaderTest {

	/** Zookeeper info */
	private static final String ZK_ADDRESS = "10.10.3.118:2181,10.10.3.118:2182,10.10.3.118:2183";
	private static final String ZK_PATH = "/zktest";

	public static void main(String[] args) throws InterruptedException {
		final LeaderSelectorListener listener = new LeaderSelectorListener() {
			@Override
			public void takeLeadership(CuratorFramework client)
					throws Exception {
				System.out.println(Thread.currentThread().getName()
						+ " take leadership!");

				// takeLeadership() method should only return when leadership is
				// being relinquished.
				Thread.sleep(5000L);

				System.out.println(Thread.currentThread().getName()
						+ " relinquish leadership!");
			}

			@Override
			public void stateChanged(CuratorFramework client,
					ConnectionState state) {
			}
		};

		new Thread(new Runnable() {

			@Override
			public void run() {
				registerListener(listener);
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				registerListener(listener);
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				registerListener(listener);
			}
		}).start();

		Thread.sleep(Integer.MAX_VALUE);
	}

	private static void registerListener(LeaderSelectorListener listener) {
		// 1.Connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS,
				new RetryNTimes(10, 5000));
		client.start();

		// 2.Ensure path
		try {
			new EnsurePath(ZK_PATH).ensure(client.getZookeeperClient());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3.Register listener
		LeaderSelector selector = new LeaderSelector(client, ZK_PATH, listener);
		selector.autoRequeue();
		selector.start();
	}
}
