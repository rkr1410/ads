package net.rkr1410.ctc.strings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.rkr1410.ctc.TestRunner;

// Given a string, check if it's a permutation of a palindrome
// (all solutions ignore spaces)
public class PalindromePermutationCh1_04 {

  private static final String BIT_VECTOR_ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public boolean isPalindrome_bitVector(String s) {
    // Ok so a-z is 26 letters, we can add another 26 A-Z for 52,
    // and slap space for 53, then add 0-9 for 63 bits with one left
    long freq = 0;
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch == ' ') {
        continue;
      }
      int charIndex = getCharIndex(ch);

      /*
       e.g. "ac" where charIndex(a) = 0, and charIndex(c) = 2
       0000(freq) ^ 0001(a) = 0001 // encountering a again will flip back to zero
       0001(freq) ^ 0100(c) = 0101 // Two bits on, can't have palindrome
       */
      long charBit = 1L << charIndex;
      freq ^= charBit;
    }
    // check if freq has at most one bit on (max one character shows in string odd number of times)
    // n & (n-1) == 0 if there is just one bit on, as n-1 will flip the bit off and all bits
    // to the right of it to on.
    // n & (n-1) != 0 if there were more bits on, as n-1 will leave the leftmost bit untouched

    return (freq & (freq - 1)) == 0;
  }


  private int getCharIndex(char c) {
    /*
      could split with if-else "switch" like:
      return c >= 'a' && c <= 'z' ? c - 'a'
           : c >= 'A' && c <= 'Z' ? 26 + c - 'A'
           : c >= '0' && c <= '9' ? 52 + c - 'A'
           : -1;
      default (-1) making the caller responsible for any throws
     */
    int index = BIT_VECTOR_ALLOWED_CHARS.indexOf(c);

    if (index >= 0) {
      return index;
    }
    throw new IllegalArgumentException("Illegal character " + c);
  }

  public boolean isPalindromePermutation(String s) {
    // I assume we can remove spaces - example provided uses space
    // which is not in the middle: Tact coa as a permutation of taco cat
    // (also seems to disregard case)

    // I guess all a palindrome needs, is at most single odd letter,
    // the rest (if present) having even count
    Map<Character, Integer> charFrequency = new HashMap<>(s.length());
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch == ' ') {
        continue;
      }
      Integer count = charFrequency.computeIfAbsent(ch, k -> 0);
      charFrequency.put(ch, count + 1);
    }

    boolean oddValueFound = false;
    for (int i : charFrequency.values()) {
      if (i % 2 != 0) {
        if (oddValueFound) {
          return false;
        } else {
          oddValueFound = true;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    PalindromePermutationCh1_04 sut = new PalindromePermutationCh1_04();
    TestRunner tr = new TestRunner();
    runTests(tr, sut::isPalindromePermutation);
    runTests(tr, sut::isPalindrome_bitVector);
  }

  private static void runTests(TestRunner tr, Function<String, Boolean> code) {
    tr.assertBoolean("abc is not a palindrome", () -> code.apply("abc"), false);
    tr.assertBoolean("a b c is not a palindrome", () -> code.apply("a b c"), false);
    tr.assertBoolean("tact coa is a palindrome", () -> code.apply("tact coa"), true);
    tr.assertBoolean("kayak is a palindrome", () -> code.apply("kayak"), true);
    tr.assertBoolean("yakyak is a palindrome", () -> code.apply("yakyak"), true);
    tr.assertBoolean("yakyaka is a palindrome", () -> code.apply("yakyaka"), true);
    tr.assertBoolean("yakyakak is not a palindrome", () -> code.apply("yakyakak"), false);
    tr.assertBoolean("uppercase and digits", () -> code.apply("1Bb2A3450A5432Bb1"), true);
  }
}
