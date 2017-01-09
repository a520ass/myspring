package com.hf.sort;

/**
 * 选择排序
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3};
        selectionSort(array);
        SortHelp.display(array);
    }

    /**
     * 选择排序的思想：选出最小的一个和第一个位置交换，选出其次小的和第二个位置交换 ……
     * 直到从第N个和第N-1个元素中选出最小的放在第N-1个位置。
     * 第一次内循环比较N - 1次，然后是N-2次，N-3次，……，最后一次内循环比较1次。
     * 共比较的次数是 (N - 1) + (N - 2) + ... + 1，求等差数列和，得 (N - 1 + 1)* N / 2 = N^2 / 2。
     * 舍去最高项系数，其时间复杂度为 O(N^2)。
     * 选择排序进行的交换操作很少，最多会发生 N - 1次交换
     *
     * @param array
     */
    private static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int lowIndex = i;    //定义一个index。假定它对应的数据最小
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[lowIndex]) {    //如果出现比它小的，记录index
                    lowIndex = j;
                }
            }
            if (lowIndex != i)
                SortHelp.swap(array, i, lowIndex);
        }
    }
}
