package study;

import java.util.List;

public class Bubble {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 343, 56, 7, 97, 15, 32, 46, 76899, 21, 1, 0};
        sort(numbers);
        for (Integer n : numbers) {
            System.out.print(", "+n);
        }
    }

    public static void sort(int [] array){
        int temp;

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length; j++){
                if(i != j){
                    if(array[i] < array[j]){
                        temp = array[j];
                        array[j] = array[i];
                        array[i] = temp;
                    }
                }
            }
        }
    }
}

/*
public class study.Bubble {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 343, 56, 7, 97, 15, 32, 46, 76899, 21, 1, 0};
        sort(numbers);
        for(Integer n : numbers){
            System.out.print(n + ", ");
        }
    }

    public static void sort(int[] array) {
        int temp = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (i != j) {
                    if (array[i] < array[j]) {
                        temp = array[j];
                        array[j] = array[i];
                        array[i] = temp;
                    }
                }
            }
        }
    }
}

 */