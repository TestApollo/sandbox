package study;


public class SelectionSort {
    public static void main(String[] args) {
        int[] numbers = new int[30];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + (int) Math.random() * 30;
        }

        for (Integer in : numbers) {
            System.out.println(in);
        }
    }

    public static void selection(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = array[i];
            int min_i = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    min_i = j;
                }
            }
            //Если нашелся элемент, меньший, чем на текущей позиции,
            //меняем их местами
            if (i != min_i) {
                int tmp = array[i];
                array[i] = array[min_i];
                array[min_i] = tmp;
            }
        }
    }
}
/*
public static void selection(int[] array) {
        //По очереди будем просматривать все подмножества
        //элементов массива (0 - последний, 1-последний,
        //2-последний,...)
        for (int i = 0; i < array.length; i++) {
            //Предполагаем, что первый элемент (в каждом
            //подмножестве элементов) является минимальным
            int min = array[i];
            int min_i = i;

            //В оставшейся части подмножества ищем элемент,
            //который меньше предположенного минимума
            for (int j = i + 1; j < array.length; j++) {
                //Если находим, запоминаем его индекс
                if (array[j] < min) {
                    min = array[j];
                    min_i = j;
                }
            }
            //Если нашелся элемент, меньший, чем на текущей позиции,
            //меняем их местами
            if (i != min_i) {
                int tmp = array[i];
                array[i] = array[min_i];
                array[min_i] = tmp;
            }
        }
    }
 */