package net.rkr1410.ctc.strings;

import java.util.*;
import java.util.function.Function;

// Check i a String contains only unique characters
// hints 44, 117, 132
public class UniqueLettersCh1_01 {


  // Basic O(n^2) lookup, uses no additional structures
  public boolean hasUniqueCharactersOnly_nSquared(String sentence) {
    Objects.requireNonNull(sentence, "sentence can't be null");
    int length = sentence.length();
    if (length < 2) {
      return true;
    }

    for (int i = 0; i < length; i++) {
      for (int j = 0; j < length; j++) {
        if (i != j && sentence.charAt(i) == sentence.charAt(j)) {
          return false;
        }
      }
    }
    return true;
  }

  // Sorting the character array with O(log(n)) allows for 
  // O(n) checking
  public boolean hasUniqueCharactersOnly_sorting(String sentence) {
	  Objects.requireNonNull(sentence, "sentence can't be null");
    int length = sentence.length();
    if (length < 2) {
      return true;
    }

    char[] sentenceCharacters = sentence.toCharArray();
    Arrays.sort(sentenceCharacters);
    for (int i = 1; i < sentenceCharacters.length; i++) {
      if (sentenceCharacters[i] == sentenceCharacters[i - 1]) {
        return false;
      }
    }
    return true;
  }

  // Use hash lookup structure with O(1) (amortized) access
	// so checking for uniqueness is mostly O(n) population
  // and lookup for possibly each character in the string
	public boolean hasUniqueCharactersOnly_hashSet(String sentence) {
	  Objects.requireNonNull(sentence, "sentence can't be null");
    int length = sentence.length();
    if (length < 2) {
      return true;
    }

		Set<Character> set = new HashSet<>(sentence.length());
		for (int i = 0; i < length; i++) {
			char ch = sentence.charAt(i);
			if (set.contains(ch)) {
				return false;
			}
			set.add(ch);
		}
		return true;
	}

	// Allow only a-z characters and a space, getting O(n) time and O(1)
	// space, by using a bit vector
	public boolean hasUniqueCharactersOnly_bitVector(String sentence) {
	  Objects.requireNonNull(sentence, "sentence can't be null");

		int occurBits = 0;
		for (int i = 0; i < sentence.length(); i++) {
			int charIndex = getCharIndex(sentence.charAt(i));
			int charBitIndex = 1 << charIndex;
		  if ((occurBits & charBitIndex) > 0) {
				return false;
			}
			occurBits |= charBitIndex;
		}
		return true;
	}

	private int getCharIndex(char ch) {
    if (ch == ' ') {
      return 'z' + 1;
    }
		if (ch < 'a' || ch > 'z') {
			throw new IllegalArgumentException("Illegal character '" + ch + "'");
		}
		return ch - 'a';
	}
	

  public static void main(String[] arguments) {
    UniqueLettersCh1_01 sut = new UniqueLettersCh1_01();
    TestRunner testRunner = new TestRunner();
		System.err.println("Brute force");
    testRunner.runTests(sut::hasUniqueCharactersOnly_nSquared);
    System.err.println("\nHashSet");
    testRunner.runTests(sut::hasUniqueCharactersOnly_hashSet);
    System.err.println("\nBitVector");
    testRunner.runTests(sut::hasUniqueCharactersOnly_bitVector);
    System.err.println("\nSorted");
    testRunner.runTests(sut::hasUniqueCharactersOnly_sorting);
  }

  private static class TestRunner {

    private void runTests(Function<String, Boolean> code) {
      assertBoolean("empty string is unique", code, "", true);
      assertBoolean("single char string is unique", code, "d", true);
      assertBoolean("alphabet is unique", code, "abcdefghijklmnopqrstuvwxyz", true);
      assertBoolean("this string is not unique", code, "this string is not unique", false);
      assertBoolean("'aa' is not unique", code, "aa", false);
      assertBoolean("quick brown fox is not unique", code, "quick brown fox", false);
      assertThrows("null arg throws npe with 'sentence can't be null'",
          code, null, java.lang.NullPointerException.class, "sentence can't be null");
    }

    public void assertBoolean(
        String testDescription,
        Function<String, Boolean> code,
        String argument, 
        boolean expected) {
      System.err.print("\"" + testDescription + "\": ");
      try {
        boolean result = code.apply(argument);
        System.err.println(result == expected ? "passed" : "failed");
      } catch (Exception ex) {
        System.err.println("Failed with " 
          + ex.getClass() 
          + ", with a message: "
          + ex.getMessage());
      } 
    }

    public void assertThrows(
        String testDescription,
        Function<String, Boolean> code,
        String argument,
        Class<? extends Throwable> expectedClass,
        String expectedMessage) {
      System.err.print("\"" + testDescription + "\": ");
      try {
        code.apply(argument);
        System.err.println("failed, expected an exception, but none thrown");
      } catch (Exception ex) {
        if (ex.getClass().equals(expectedClass)
          && Objects.equals(ex.getMessage(), expectedMessage)) {
          System.err.println("passed");
        } else {
          System.err.println("failed. Expected a "
            + expectedClass.getName()
            + " with message:\n\""
            + expectedMessage
            + "\"\nbut got a "
            + ex.getClass().getName()
            + " with message:\n"
            + getExceptionMessage(ex));
        }
      }
    }

    private String getExceptionMessage(Exception ex) {
      String message = ex.getMessage();
      if (message == null) {
        return "null";
      } else {
        return "\"" + message + "\"";
      }
    }
  }

}
