package com.hf.test;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;

import com.hf.config.hadoop.DatasetConfig;
import com.hf.config.hadoop.HadoopConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HadoopConfig.class,DatasetConfig.class})
public class HadoopTest {
	
	/*@Autowired
	private DatasetOperations datasetOperations;
	@Autowired
	private DataStoreWriter<FileInfo> writer;*/
	
	@Autowired
	private FileSystem fileSystem;
	
	
	@Test
	public void upload() throws IOException{
		//fileSystem.getStatus();
		FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/user/hadoop/test.jpg"), true);
		
		InputStream inputStream=new FileInputStream("D:/tmp/test.jpg");
		FileCopyUtils.copy(inputStream, fsDataOutputStream);
	}
	
	@Test
	public void download() throws IllegalArgumentException, IOException{
		FSDataInputStream fsDataInputStream = fileSystem.open(new Path("/hfdefile/test.jpg"));
		OutputStream outputStream= new FileOutputStream("D:/tmp/test1.jpg");
		FileCopyUtils.copy(fsDataInputStream, outputStream);
	}
	
	@Test
	public void delete() throws IllegalArgumentException, IOException{
		boolean delete = fileSystem.delete(new Path("/hdfs.test1"), true);
	}

}

