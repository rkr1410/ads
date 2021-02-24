package net.rkr1410.ctc.strings;

import net.rkr1410.ctc.TestRunner;

import java.util.*;
import java.util.function.Function;

// Check if a String contains only unique characters
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
    runTests(testRunner, sut::hasUniqueCharactersOnly_nSquared);
    System.err.println("\nHashSet");
    runTests(testRunner, sut::hasUniqueCharactersOnly_hashSet);
    System.err.println("\nBitVector");
    runTests(testRunner, sut::hasUniqueCharactersOnly_bitVector);
    System.err.println("\nSorted");
    runTests(testRunner, sut::hasUniqueCharactersOnly_sorting);
  }

  private static void runTests(TestRunner tr, Function<String, Boolean> code) {
    tr.assertBoolean("empty string is unique", () -> code.apply(""), true);
    tr.assertBoolean("single char string is unique", () -> code.apply("d"), true);
    tr.assertBoolean("alphabet is unique", () -> code.apply("abcdefghijklmnopqrstuvwxyz"), true);
    tr.assertBoolean("this string is not unique", () -> code.apply("this string is not unique"), false);
    tr.assertBoolean("'aa' is not unique", () -> code.apply("aa"), false);
    tr.assertBoolean("quick brown fox is not unique", () -> code.apply("quick brown fox"), false);
    tr.assertThrows("null arg throws npe with 'sentence can't be null'",
        () -> code.apply(null), java.lang.NullPointerException.class, "sentence can't be null");
  }


}
