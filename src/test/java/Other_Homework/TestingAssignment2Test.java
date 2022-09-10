package Other_Homework;

import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
@Test
public class TestingAssignment2Test {
        TestingAssignment2 two = new TestingAssignment2();
        int[] arr1 = new int[]{1, 4, 1, 4};
        int[] arr2 = new int[]{1, 1, 1, 1};
        int[] arr3 = new int[]{4, 4, 4, 4};
        int[] arr4 = new int[0];
    @Test
    public void checkForBoth () {
        Assertions.assertEquals(true, two.checkForBoth(arr1));
        Assertions.assertEquals(false, two.checkForBoth(arr2));
        Assertions.assertEquals(false, two.checkForBoth(arr2));
        Assertions.assertEquals(false, two.checkForBoth(arr4));
    }
}