package com.hf.sort;

/**
 * 冒泡排序
 */
public class BubbleSort {
	
	public static void main(String[] args) {
		int[] array = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3 };
		bubbleSort(array);
		for (int i : array) {
			System.out.print(i+" ");
		}
	}

	private static void bubbleSort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = i+1; j < array.length; j++) {
				if(array[j]<array[i]){
					SortHelp.swap(array, i, j);
				}
			}
		}
	}
}
