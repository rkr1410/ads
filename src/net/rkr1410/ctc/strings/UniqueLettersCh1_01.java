package net.rkr1410.ctc.strings;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.Function;

// hints 44, 117, 132
public class UniqueLettersCh1_01 {
	
	public boolean noAdditionalStructures(String s) {
		Objects.requireNonNull(s, "argument can't be null");
		if (s.length() <= 1) {
			return true;
		}
		
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < s.length(); j++) {
				if ((i != j) && (s.charAt(i) == s.charAt(j))) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean nLogNWithSorting(String s) {
		Objects.requireNonNull(s, "argument can't be null");
		if (s.length() <= 1) {
			return true;
		}
		
		char[] sAsArray = s.toCharArray();
		// O(n log(n))
		Arrays.sort(sAsArray);
		
		// O(n)
		for (int i = 1; i < sAsArray.length; i++) {
			if (sAsArray[i] == sAsArray[i - 1]) {
				return false;
			}
		}
		return true;
	}

	public boolean bitChecking(String s) {
        Objects.requireNonNull(s, "argument can't be null");
        int charsAsBits = 0;

	    for (int i = 0; i < s.length(); i++) {
	        char ch = s.charAt(i);
            checkLowercaseLetter(ch);
            if ((charsAsBits & (1 << ch)) > 0) {
                return false;
            }
            charsAsBits |= 1 << ch;
        }
	    return true;
    }

    private void checkLowercaseLetter(char ch) {
	    if (ch < 'a' || ch > 'z') {
	        throw new IllegalArgumentException("Illegal character '" + ch + "' found");
        }
    }

	public static void main(String args[]) {
		UniqueLettersCh1_01 test = new UniqueLettersCh1_01();
		new TestRunner().runTests(test);
	}
	
	private static class TestRunner {
		void runTests(UniqueLettersCh1_01 sut) {
            List<Function<String, Boolean>> testFunctions = Arrays.asList(sut::nLogNWithSorting, sut::noAdditionalStructures, sut::bitChecking);
            List<String> trueParams = Arrays.asList("", "x", "abcdefghijklmnopqrstuvwxyz");

            testFunctions.forEach(f -> trueParams.forEach(s -> assertTrue(f.apply(s))));
            testFunctions.forEach(f -> assertFalse(f.apply("abza")));
            testFunctions.forEach(f -> assertThrowsNPE(() -> f.apply(null)));
		}

        private void assertThrowsNPE(Supplier<Boolean> test) {
            try {
                test.get();
                System.err.println("failed");
            } catch (NullPointerException npe) {
                System.err.println("passed");
            }
        }

        private void assertFalse(boolean b) {
            if (!b) {
                System.err.println("passed");
            } else {
                System.err.println("failed");
            }
        }

        private void assertTrue(boolean b) {
            if (b) {
                System.err.println("passed");
            } else {
                System.err.println("failed");
            }
        }
	}
	
}
