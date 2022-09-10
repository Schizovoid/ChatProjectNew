package Other_Homework;

import java.util.ArrayList;
import java.util.Arrays;

//Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
//Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов, идущих после последней четверки.
//Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо выбросить RuntimeException.
//Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
public class TestingAssignment1 {
    public static Integer[] returnAfterFour(Integer[] before) {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(before));
        if (!list.contains(4)) {
            throw new RuntimeException("Massive should contain a number 4!");
        }
            while (list.contains(4)) {
                    list.remove(list.iterator().next());
                }
            Integer[] result = new Integer[list.size()];
            list.toArray(result);
            return result;
        }
    }
