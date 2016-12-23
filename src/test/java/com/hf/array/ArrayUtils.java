package com.hf.array;

import java.lang.reflect.Array;

public class ArrayUtils {
	
	/**
	 * 在数组末尾增加数据
	 * @param srcarray
	 * @param s
	 * @return 
	 */
	public static <T> T[] add(T[] srcarray, T s) {
		return add(srcarray, srcarray.length, s);
	}
	
	/**
	 * 在数组指定位置增加数据，同时后面的数据向后移动一位
	 * @param srcarray
	 * @param index 
	 * @param s
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] add(T[] srcarray, int index, T s) {
		if (index > srcarray.length) {
			throw new IllegalArgumentException("index 非法");
		}
		// getComponentType()返回表示数组的组件类型的类。
		T[] destarray = (T[]) Array.newInstance(srcarray.getClass()
				.getComponentType(), srcarray.length + 1);
		// T[] destarray = (T[])new Object[srcarray.length+1];//类型转换出错
		// T[] destarray =new T[srcarray.length+1];// error: generic array creation
		System.arraycopy(srcarray, 0, destarray, 0, index);
		destarray[index] = s;
		System.arraycopy(srcarray, index, destarray, index + 1, srcarray.length
				- index);
		return destarray;
	}
	
	/**
	 * 删除数组的指定位
	 * @param srcarray
	 * @param index
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] remove(T[] srcarray, int index) {
		if (index >= srcarray.length) {
			throw new IllegalArgumentException("index 非法");
		}
		T[] destarray = (T[]) Array.newInstance(srcarray.getClass()
				.getComponentType(), srcarray.length - 1);
		System.arraycopy(srcarray, 0, destarray, 0, index);
		System.arraycopy(srcarray, index + 1, destarray, index, srcarray.length
				- index - 1);
		return destarray;
	}
}
