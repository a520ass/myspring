package com.hf.sort;

public class SelectionSort {
	
	public static void main(String[] args) {
		int[] array = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3 };
		selectionSort(array);
		SortHelp.display(array);
	}

	private static void selectionSort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int lowIndex = i;	//定义一个index。假定它对应的数据最小
			for (int j=i+1; j < array.length; j++) {
				if (array[j] < array[lowIndex]) {	//如果出现比它小的，记录index
					lowIndex = j;
				}
			}
			SortHelp.swap(array, i, lowIndex);
		}
	}
}
