package com.hf.utils.fastdfs;

import java.io.FileNotFoundException;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastdfsClient {
	public static Logger logger = LoggerFactory.getLogger(FastdfsClient.class);

	private static volatile FastdfsClient fdfsClient;
	private TrackerClient tracker;

	private FastdfsClient() {
		try {
			String path = FastdfsClient.class.getResource("/fdfs_client.conf").getPath();
			ClientGlobal.init(path);
			tracker = new TrackerClient();
		} catch (FileNotFoundException e) {
			logger.error("未找到fdfs对应的配置文件!",e);
		} catch (Exception e) {
			logger.error("初始化FastdfsClient报错!",e);
		}
	}

	public static FastdfsClient getInstance() {
		if (fdfsClient == null) {
			synchronized (FastdfsClient.class) {
				if (fdfsClient == null) {
					fdfsClient = new FastdfsClient();
				}
			}
		}
		return fdfsClient;
	}

	public String upload(String localFilePath) throws Exception {
		return upload(localFilePath, null);
	}

	public String upload(byte[] fileContent) throws Exception {
		return upload(fileContent, null);
	}

	public String upload(String localFilePath, NameValuePair[] metaData) throws Exception {
		TrackerServer trackerServer = null;
		StorageClient1 storageClient = null;

		String master_file_id = null;
		try {
			trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			storageClient = new StorageClient1(trackerServer, storageServer);

			master_file_id = storageClient.upload_file1(localFilePath, null, metaData);
		} finally {
			if (trackerServer != null) {
				trackerServer.close();
			}
		}
		return master_file_id;
	}

	public String upload(byte[] fileContent, NameValuePair[] metaData) throws Exception {
		TrackerServer trackerServer = null;
		StorageClient1 storageClient = null;

		String master_file_id = null;
		try {
			trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			storageClient = new StorageClient1(trackerServer, storageServer);

			master_file_id = storageClient.upload_file1(fileContent, "fdfs", metaData);
		} finally {
			if (trackerServer != null) {
				trackerServer.close();
			}
		}
		return master_file_id;
	}

	public NameValuePair[] getMetaData(String fileId) throws Exception {
		TrackerServer trackerServer = null;
		StorageClient1 storageClient = null;

		NameValuePair[] nvPair = null;
		try {
			trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			storageClient = new StorageClient1(trackerServer, storageServer);

			nvPair = storageClient.get_metadata1(fileId);
		} finally {
			if (trackerServer != null) {
				trackerServer.close();
			}
		}
		return nvPair;
	}

	/**
	 * 删除文件（注意tracker对应的nginx下的缓存）
	 * 
	 * @param fileId 上传时候返回的路径即为唯一标示
	 * @return 删除是否成功标志
	 * 
	 * @throws Exception
	 */
	public boolean delete(String fileId) throws Exception {
		TrackerServer trackerServer = null;
		StorageClient1 storageClient = null;

		boolean success = false;
		try {
			trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			storageClient = new StorageClient1(trackerServer, storageServer);

			if (storageClient.delete_file1(fileId) == 0) {
				success = true;
			}
		} finally {
			if (trackerServer != null) {
				trackerServer.close();
			}
		}
		return success;
	}

	/**
	 * 下载文件的二进制
	 * 
	 * @param fileId 上传时候返回的路径
	 * @return 文件对应的二进制数组
	 * @throws Exception
	 */
	public byte[] download(String fileId) throws Exception {
		TrackerServer trackerServer = null;
		StorageClient1 storageClient = null;

		byte[] fileContent = null;
		try {
			trackerServer = tracker.getConnection();
			StorageServer storageServer = null;
			storageClient = new StorageClient1(trackerServer, storageServer);

			fileContent = storageClient.download_file1(fileId);
		} finally {
			if (trackerServer != null) {
				trackerServer.close();
			}
		}
		return fileContent;
	}
	
	public static void main(String[] args) throws Exception {
		FastdfsClient fc=new FastdfsClient();
		//String upload = fc.upload("D:/tmp/ea365534-8479-4abf-9961-1b67a25276f1.jpg");
		//System.out.println(upload);
		//byte[] bytecontent = fc.download("group1/M00/00/00/wKiZgFeBv1qAQvnPAABfzbL_L8Q801.jpg");
		//FileCopyUtils.copy(bytecontent, new File("d:/tmp/test.jpg"));
		//NameValuePair[] metaData = fc.getMetaData("group1/M00/00/00/wKiZgFeBv1qAQvnPAABfzbL_L8Q801.jpg");
		//System.out.println(metaData.toString());
	}
	

}