package com.hf.sort;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3};
        bubbleSort1(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }

    /**
     * 对于n位的数列则有比较次数为 (n-1) + (n-2) + ... + 1 = n * (n - 1) / 2，这就得到了最大的比较次数(等差队列。求和)
     * 而O(N^2)表示的是复杂度的数量级。（按照计算复杂度的原则，去掉常数，去掉最高项系数）
     * 举个例子来说，如果n = 10000，那么 n(n-1)/2 = (n^2 - n) / 2 = (100000000 - 10000) / 2，
     * 相对10^8来说，10000小的可以忽略不计了，所以总计算次数约为0.5 * N^2。用O(N^2)就表示了其数量级（忽略前面系数0.5）。
     *
     */

    /**
     * 冒泡排序的思想，是让最大的数浮动到数组最后的位置，其次大的数浮动到数组倒数第二个位置……
     * 当然，你也可以从大到小排序，也可以从后向前冒泡。其特征操作是相邻元素的比较和交换
     * 时间复杂度 O(n^2)
     * 冒泡排序最坏的情况下要发生N^2 /2交换操作
     *
     * @param array
     */
    private static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j + 1] < array[j]) {
                    SortHelp.swap(array, j, j + 1);
                }
            }
        }
    }

    /**
     * 优化算法  最好的情况下 时间复杂度 O(n)
     * [9, 3, 4, 5, 7]
     * 3, 4, 5, 7, 9 (i = 1)
     * 3, 4, 5, 7, 9 (i = 2)
     * 3, 4, 5, 7, 9 (i = 3)
     * 3, 4, 5, 7, 9 (i = 4)
     * http://www.cnblogs.com/jiqingwu/p/bubble_sort_analysis.html
     */
    private static void bubbleSort1(int[] array) {
        boolean didSwap;
        for (int i = 0; i < array.length - 1; i++) {
            didSwap = false;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j + 1] < array[j]) {
                    SortHelp.swap(array, j, j + 1);
                    didSwap = true;
                }
            }
            if (didSwap == false)//当某一次内层循环中，相邻的元素没有发生交换，就说明数组已经是有序的了，这时可以跳出循环。
                return;
        }
    }
}
