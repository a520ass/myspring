package com.hf.file;

import java.io.File;
/**
 * java 中 public 类的名字必须和.java文件名相同
 * 而且main函数只能在public类中运行，若main函数出现在普通的class中，则当作普通的函数调用(见下面的svn类的用法)
 * 没有规定java类中必须要有一个类和.java文件名相同 。
 * @author 520
 *
 */
class Svn1 {
	
	public static void main(String[] args) {
		String pathname="G:/yonyoudata/新建文件夹1/1.doc";
		File f=new File(pathname);
		if(f.exists()){
			delDotsvn(f);
		}else{
			System.err.println(String.format("No such file or directory => %s",f.getAbsolutePath()));
		}
		
	}
	
	public static void delDotsvn(File f){
		
		File[] files = f.listFiles();
		if(files!=null){
			for (File file : files) {
				if(".svn".equals(file.getName())){
					del(file);
				}else{
					//循环调用此方法。看当前文件夹下是否有需要删除的（这里把文件也当作文件夹来处理）
					delDotsvn(file);
				}
			}
		}
	}
	
	/**
	 * 循环删除所有的文件，文件夹（包括非空文件夹）
	 * @param file
	 */
	private static void del(File file) {
		File[] files = file.listFiles();
		if(files!=null){
			for (File file2 : files) {
				del(file2);
			}
		}
		file.delete();
	}
}

public class SvnClean{
	public static void main(String[] args) {
		
		Svn1.main(args);
		
	}
}