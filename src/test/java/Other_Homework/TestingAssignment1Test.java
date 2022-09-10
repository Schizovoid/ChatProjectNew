package Other_Homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

@Test
public class TestingAssignment1Test {
    private Integer[] wrongArr = new Integer[] {1,2,3};



    @Test
    public void checkException() {
        Assertions.assertThrows(RuntimeException.class, ()->
                TestingAssignment1.returnAfterFour(wrongArr));
    }
    public static Stream<Arguments> getSomeData() {
        ArrayList <Arguments> args = new ArrayList<>();
        args.add(Arguments.arguments(new Integer[] {1,2,3,4,5,6,7} , new Integer[] {5,6,7}));
        args.add(Arguments.arguments(new Integer[] {7,6,5,4,3,2,1} , new Integer[] {3,2,1}));
        args.add(Arguments.arguments(new Integer[] {1,4,1,4,1} , new Integer[] {1}));
        return args.stream();
    }

    //Здесь возникает ошибка
    //Cannot inject @Test annotated Method [testForFourWithParam] with [class [Ljava.lang.Integer;, class [Ljava.lang.Integer;].

    @ParameterizedTest
    @MethodSource ("getSomeData")
    public void testForFourWithParam (Integer[] before, Integer[] after) {
    Assertions.assertArrayEquals(after, TestingAssignment1.returnAfterFour(before));
    }

    //Здесь работает нормально
    @Test
    public void testForFourWithoutParam () {
        Integer[] before = new Integer[] {1,2,3,4,5,6,7};
        Integer[] expected = new Integer[] {5,6,7};
        Assertions.assertArrayEquals(expected, TestingAssignment1.returnAfterFour(before));
        Integer[] before2 = new Integer[] {7,6,5,4,3,2,1};
        Integer[] expected2 = new Integer[] {3,2,1};
        Assertions.assertArrayEquals(expected2, TestingAssignment1.returnAfterFour(before2));
        Integer[] before3 = new Integer[] {1,4,1,4,1};
        Integer[] expected3 = new Integer[] {1};
        Assertions.assertArrayEquals(expected3, TestingAssignment1.returnAfterFour(before3));
    }
}