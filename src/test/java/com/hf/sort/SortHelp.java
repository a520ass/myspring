package com.hf.sort;

public class SortHelp {
	
	/**
	 * 交换
	 * @param array
	 * @param aindex
	 * @param bindex
	 */
	public static void swap(int[] array,int aindex,int bindex){
		int temp = array[aindex];                      
		array[aindex] = array[bindex];                        
		array[bindex] = temp;
	}
	
	public static void display(int[] array){
		for (int i : array) {
			System.out.print(i+" ");
		}
	}
}
