package net.rkr1410.ctc.strings;

import java.util.function.BiFunction;
import net.rkr1410.ctc.TestRunner;

// Three types of edits can be performed on a String: insert/remove/replace
// a character. Write a method to check if two Strings are at most one edit away
public class OneEditAllowedCh1_05 {

  public boolean isMaxOneEditAway_byCharacter(String s1, String s2) {
    if (s1 == null || s2 == null) {
      throw new NullPointerException("Both arguments have to be non null");
    }
    if (s1.equals(s2)) {
      return true;
    }
    int len1 = s1.length();
    int len2 = s2.length();
    if (len1 <= 1 && len2 <= 1) {
      return true;
    }
    if (Math.abs(len2 - len1) > 1) {
      return false;
    }
    if (len1 == len2) {
      return atMostOneCharacterIsDifferent(s1, s2);
    }

    boolean foundEdit = false;
    int shorterIndex = 0;
    int longerIndex = 0;
    String shorter = s1.length() > s2.length() ? s2 : s1;
    String longer = s1.length() > s2.length() ? s1 : s2;

    while (true) {
      char c1 = longer.charAt(longerIndex);
      char c2 = shorter.charAt(shorterIndex);

      // if the characters are equal...
      if (c1 == c2) {
        //...and we're at the end of shorter string...
        if (shorterIndex == shorter.length() - 1) {
          // if we're also at the end of the longer one, this means the only change so far
          // was an insert. And since the end chars are matching, it's the only edit that was made
          if (longerIndex == longer.length() - 1) {
            return true;
          } else {
            // if we're not at the end of the longer one, this means there's an extra 'dangling' character
            // at the end of it. It's an edit, so we return based on if there was a replace found earlier.
            return !foundEdit;
          }
        }
        //...and we're not at the end of the shorter string, keep looking
        shorterIndex++;
        longerIndex++;
      } else {
        // if the characters are not equal...

        //...and we found an edit previously, just return false
        if (foundEdit) {
          return false;
        }

        //...and we have not found any edit yet...

        //...and we're at the end of shorter string
        if (shorterIndex >= shorter.length() - 1) {
          // If we're also at the end of the longer one, this is the sole edit
          if (longerIndex == longer.length() - 1) {
            return true;
          }
          // if we're not at the end of the longer one, the char after it must be equal to current shorter,
          // or else there are two edits
          return (longer.charAt(longerIndex + 1) == shorter.charAt(shorterIndex));
        }

        // So, the current chars are not equal, but if shorter char is like
        // next longer one, let these be current indices and we assume insert
        // was made. If they're not equal, more than one insert was required.
        if (longer.charAt(longerIndex + 1) == shorter.charAt(shorterIndex)) {
          longerIndex += 2;
          shorterIndex++;
          foundEdit = true;
        } else {
          return false;
        }
      }
    }
  }

  private boolean atMostOneCharacterIsDifferent(String s1, String s2) {
    boolean foundDifference = false;
    for (int i = 0; i < s1.length(); i++) {
      if (s1.charAt(i) != s2.charAt(i)) {
        if (foundDifference) {
          return false;
        } else {
          foundDifference = true;
        }
      }
    }
    return true;
  }

  public boolean isMaxOneEditAway_bruteForce(String s1, String s2) {
    if (s1 == null || s2 == null) {
      throw new NullPointerException("Both arguments have to be non null");
    }
    if (s1.equals(s2)) {
      return true;
    }
    int len1 = s1.length();
    int len2 = s2.length();
    if (len1 <= 1 && len2 <= 1) {
      return true;
    }
    if (Math.abs(len2 - len1) > 1) {
      return false;
    }
    if (len1 == len2) {
      return atMostOneCharacterIsDifferent(s1, s2);
    }

    String longer = len1 > len2 ? s1 : s2;
    String shorter = len1 < len2 ? s1 : s2;

    StringBuilder sb = new StringBuilder(longer);
    for (int i = 0; i < longer.length(); i++) {
      char ch = longer.charAt(i);
      sb.deleteCharAt(i);
      if (shorter.equals(sb.toString())) {
        return true;
      }
      sb.insert(i, ch);
    }
    return false;
  }

  public static void main(String[] args) {
    OneEditAllowedCh1_05 sut = new OneEditAllowedCh1_05();
    TestRunner tr = new TestRunner();
    runTests(tr, sut::isMaxOneEditAway_bruteForce);
    runTests(tr, sut::isMaxOneEditAway_byCharacter);
  }

  private static void runTests(TestRunner tr, BiFunction<String, String, Boolean> code) {
    tr.assertBoolean("A longer sentence with insert&replace", () -> code.apply("azc", "abcd"), false);
    tr.assertBoolean("A longer sentence with insert&replace", () -> code.apply("abc", "abcd"), true);
    tr.assertBoolean("A longer sentence with insert&replace", () -> code.apply("abcdefghi", "abcdefghij"), true);
    tr.assertBoolean("A longer sentence with insert&replace", () -> code.apply("abcdezghi", "abcdefghij"), false);

  }

}