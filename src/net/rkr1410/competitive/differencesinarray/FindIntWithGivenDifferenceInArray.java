package net.rkr1410.competitive.differencesinarray;

import java.util.*;
import java.util.stream.*;

public class FindIntWithGivenDifferenceInArray {
    static int[] numbers = new int[] {1, 7, 5, 9, 2, 12, 3};
    static int difference = 2;

	private void bruteForce() {
		int length = numbers.length;
		for (int i = 0; i < length; i++) {
			for (int j = i; j < length; j++) {
				int current = numbers[i];
				int possibleMatch = numbers[j];
				if (current == possibleMatch + difference || current == possibleMatch - difference) {
					System.out.println("(" + current + ", " + possibleMatch + ")");
				}
			}
		}
	}

	private void optimized() {
		Set<Integer> set = new HashSet<>();
        for (int i : numbers) {
            set.add(i);
        }

		for (Integer i : set) {
            int match = -1;
            if (set.contains(i + 2)) {
                match = i + 2;
            } else if (set.contains(i - 2)) {
                match = i - 2;
            }

		    if (match >= 0) {
                System.err.println("(" + i + ", " + match + ")");
            }
        }
	}

	public static void main(String[] args) {
		new FindIntWithGivenDifferenceInArray().optimized();
	}
}
