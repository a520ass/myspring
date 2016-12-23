package com.hf.test;


import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hf.config.zk.ZKConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZKConfig.class)
@TestPropertySource("classpath:zk-test.conf")
public class ZKTest {
	
	
	@Autowired CuratorFramework curatorFramework;
	
	@Test
	public void zkTest() throws Exception{
		try {
			curatorFramework.create().creatingParentsIfNeeded()
			//.withMode(CreateMode.EPHEMERAL)
			.forPath("/hftest");
//			curatorFramework.getData().forPath("/dubbo");
//			List<String> path = curatorFramework.getChildren().forPath("/rpc");
//			System.out.println(curatorFramework.getState());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

