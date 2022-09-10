package Other_Homework;

import java.util.stream.IntStream;

//Написать метод, который проверяет состав массива из чисел 1 и 4.
//Если в нем нет хоть одной четверки или единицы, то метод вернет false;
//Написать набор тестов для этого метода (по 3-4 варианта входных данных).
public class TestingAssignment2 {

    public boolean checkForBoth(int[] arr) {
        return checkForOne(arr) && checkForFour(arr);
    }

    public boolean checkForFour (int[] arr) {
        return IntStream.of(arr).anyMatch(x -> x==4);
    }
    public boolean checkForOne (int[] arr) {
        return IntStream.of(arr).anyMatch(x -> x==1);
    }
}
