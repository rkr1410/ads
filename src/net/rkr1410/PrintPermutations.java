package net.rkr1410;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PrintPermutations {

    private List<String> getPermutations(String word, List<String> foundSoFar) {
		if (word.length() == 0) {
			return foundSoFar;
		}
		
		String firstCharacter = String.valueOf(word.charAt(0));
		String restOfTheWord = word.substring(1);
		if (foundSoFar.isEmpty()) {
			return getPermutations(restOfTheWord, Collections.singletonList(firstCharacter));
		} else {
			List<String> currentWordList = new ArrayList<>();
			for (String received : foundSoFar) {
				for (int i = 0; i <= received.length(); i++) {
					currentWordList.add(new StringBuilder(received).insert(i, firstCharacter).toString());
				}
			}
			return getPermutations(restOfTheWord, currentWordList);
		}
    }

	public List<String> getPermutations(String word) {
		Objects.requireNonNull(word, "word can't be null");
		return getPermutations(word, Collections.emptyList());
	}

    public static void main(String[] args) {
        // tests
        PrintPermutations test = new PrintPermutations();
        // assert test.getPermutations(null) throws NPE
        // assert test.getPermutations("a") containsExactly "a"
        // assert test.getPermutations("ab") containsExactlyInAnyOrder "ab" "bc"
        // assert test.getPermutations("abc") containsExactlyInAnyOrder "abc" "acb" "bac" "bca" "cab" "cba"
        // assert test.getPermutations("abcde").size() == 120

		try {
			test.getPermutations(null);
			System.err.println("failed, expected an NPE for null word argument");
		} catch (NullPointerException npe) {
			System.err.println("passed");
		}
		
		if (test.getPermutations("").size() == 0) {
            System.err.println("passed");
        } else {
            System.err.println("failed 0");
        }

		List<String> result = test.getPermutations("a");
		if (result != null && result.size() == 1 && "a".equals(result.get(0))) {
			System.err.println("passed");
		} else {
			System.err.println("failed, expected a single result of \"a\"");
		}
		
		if (test.getPermutations("ab").size() == 2) {
            System.err.println("passed");
        } else {
            System.err.println("failed 2");
        }

        if (test.getPermutations("abc").size() == 6) {
            System.err.println("passed");
        } else {
            System.err.println("failed 3");
        }

        if (test.getPermutations("abcd").size() == 24) {
            System.err.println("passed");
        } else {
            System.err.println("failed 4");
        }

        if (test.getPermutations("abcde").size() == 120) {
            System.err.println("passed");
        } else {
            System.err.println("failed 5");
        }
    }
}
