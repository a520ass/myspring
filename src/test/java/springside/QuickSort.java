package springside;

public class QuickSort {
	
	public static void main(String[] args) {  
        int[] array = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3 };  
        quickSort(array);  
        for (int i : array) {
			System.out.print(i+" ");
		}
    }  
  
    public static void quickSort(int[] array) {  
        subQuickSort(array, 0, array.length - 1);  
    }  
  
    private static void subQuickSort(int[] array, int start, int end) {  
        if (array == null || (end - start + 1) < 2) {  
            return;  
        }  
  
        int part = partition(array, start, end);  
  
        if (part == start) {  
            subQuickSort(array, part + 1, end);  
        } else if (part == end) {  
            subQuickSort(array, start, part - 1);  
        } else {  
            subQuickSort(array, start, part - 1);  
            subQuickSort(array, part + 1, end);  
        }  
    }  
  
    private static int partition(int[] array, int start, int end) {  
        int value = array[end];  
        int index = start - 1;  
  
        for (int i = start; i < end; i++) {  
            if (array[i] < value) {  
                index++;  
                if (index != i) {  
                	int temp = array[index];  
                    array[index] = array[i];  
                    array[i] = temp; 
                }  
            }  
        }  
  
        if ((index + 1) != end) {  
        	int temp = array[index+1];  
            array[index+1] = array[end];  
            array[end] = temp;  
        }  
  
        return index + 1;  
    }  
}
