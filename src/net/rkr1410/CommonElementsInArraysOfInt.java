package net.rkr1410;

import java.util.*;
import java.util.stream.Collectors;

public class CommonElementsInArraysOfInt {
    private static final int[] array1 = new int[]{13, 27, 35, 40, 49, 55, 59};
    private static final int[] array2 = new int[]{17, 35, 39, 40, 55, 58, 60};

    public static void main(String[] args) {
        int steps = 0;
        int j = 0;
        int length = array1.length;
        for (int first : array1) {
            while (j < length) {
                steps++;
                int second = array2[j];

                if (first == second) {
                    System.err.println(first);
                    j++;
                    break;
                } else if (second > first) {
                    break;
                } else {
                    j++;
                }
            }
        }
        System.err.println("-----> " + steps);
    }

    public static void bruteForce(String[] args) {
        for (int k : array1) {
            for (int i : array2) {
                if (k == i) {
                    System.err.println(k);
                    break;
                }
            }
        }
    }

    public static void oNPlusO1(String[] args) {
        Set<Integer> secondSet = Arrays.stream(array2).boxed().collect(Collectors.toSet());
        for (int i : array1) {
            if (secondSet.contains(i)) {
                System.err.println(i);
            }
        }
    }

}
