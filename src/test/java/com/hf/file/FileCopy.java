package com.hf.file;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.util.FileCopyUtils;

public class FileCopy {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		File srcdir = new File("D:/tmp/logs");
		File[] srcfiles = srcdir.listFiles();
		File targetdir = new File("D:/tmp/targetdir");
		long startTimeMillis = System.currentTimeMillis();
		//doCopyBySingleThread(srcfiles,targetdir);
		doCopyByMultiThread(srcfiles, targetdir);
		long endTimeMillis = System.currentTimeMillis();
		System.out.println("Speed time "+(endTimeMillis-startTimeMillis));
	}

	private static void doCopyBySingleThread(File[] srcfiles, File targetdir) throws IOException {
		for (int i = 0; i < srcfiles.length; i++) {
			FileCopyUtils.copy(srcfiles[i], new File(targetdir.getPath()+File.separator+srcfiles[i].getName()) );
		}
	}
	
	private static void doCopyByMultiThread(File[] srcfiles, File targetdir) throws InterruptedException{
        final CountDownLatch begin = new CountDownLatch(1);  
        final CountDownLatch end = new CountDownLatch(srcfiles.length);  
        final ExecutorService exec = Executors.newFixedThreadPool(srcfiles.length);  

        for (int i = 0; i < srcfiles.length; i++) {
            final int NO = i;  
            Runnable run = new Runnable() {
                public void run() {  
                    try {  
                        // 如果当前计数为零，则此方法立即返回。
                        // 等待
                        begin.await();  
                        FileCopyUtils.copy(srcfiles[NO], new File(targetdir.getPath()+File.separator+srcfiles[NO].getName()) );
                    } catch (Exception e) {  
                    	e.printStackTrace();
                    } finally {  
                        // 每个copy完成，end就减一
                        end.countDown();
                    }  
                }  
            };  
            exec.submit(run);
        }  
        begin.countDown();  
        end.await();  
        exec.shutdown();
	}
}
