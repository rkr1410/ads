package net.rkr1410.ctc.strings;

import net.rkr1410.ctc.TestRunner;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

// Given two strings, write a method to to check if one is permutation of the other
// Hints 1, 84, 122, 131
public class CheckPermutationCh1_02 {
  static List<List<String>> lst = new ArrayList<>();

  /**
   * Generate all permutations of {@code first}, and check if {@code second} matches one of these.
   * O(n!) time. Can also be O(n!) space, depending on the implementation of last parameter.
   *
   * @param first one of the two words to check
   * @param second second of the two words to check
   * @param permutationsSupplier a method returning permutations of a given String
   * @return if {@code first} is a permutation of {@code second}
   */
  public boolean isPermutation_bruteForceGen(String first, String second, Function<String, List<String>> permutationsSupplier) {
    // should null be a permutation of null?
    if (Objects.equals(first, second)) {
      return true;
    }
    if (commonChecksFailed(first, second)) {
      return false;
    }

    return permutationsSupplier.apply(first).contains(second);
  }

  // To be used as an argument for isPermutation_bruteForceGen, to iteratively generate permutations of a given string
  private List<String> getPermutationsIter(String word) {
    int length = word.length();
    if (length > 10) {
      throw new IllegalArgumentException("word can be at most 10 characters (got " + length + ")");
    }
    List<String> perms = new ArrayList<>(2);

    perms.add(String.valueOf(word.charAt(0)));
    for (int i = 1; i < length; i++) {
      List<String> currentPerms = new ArrayList<>(factorial(i) + 1);
      Character currChar = word.charAt(i);
      // Could actually iterate from len to 0, and remove each 'rcvd'
      // This way there's no need for two lists k! and (k-1)! size in memory at once
      for (String rcvd : perms) {
        StringBuilder currWord = new StringBuilder(rcvd);
        for (int j = 0; j <= rcvd.length(); j++) {
          currWord.insert(j, currChar);
          currentPerms.add(currWord.toString());
          currWord.deleteCharAt(j);
        }
      }
      perms = currentPerms;
    }
    return perms;
  }

  // To be used as an argument for isPermutation_bruteForceGen, to recursively generate permutations of a given string
  private List<String> getPermutationsRecursive(String word) {
    int length = word.length();
    if (length > 10) {
      throw new IllegalArgumentException("word can be at most 10 characters (got " + length + ")");
    }
    return getPermutationsRec(word, Collections.emptyList());
  }

  private List<String> getPermutationsRec(String s, List<String> lastStep) {
    if (lastStep.isEmpty()) {
      return getPermutationsRec(s.substring(1), Collections.singletonList(s.substring(0, 1)));
    }
    if (s.isEmpty()) {
      return lastStep;
    }

    int lastSize = lastStep.get(0).length();
    char ch = s.charAt(0);
    List<String> nextStep = new ArrayList<>(factorial(lastSize + 1) + 1);
    for (String prev : lastStep) {
      StringBuilder sb = new StringBuilder(prev);
      for (int i = 0; i <= lastSize; i++) {
        sb.insert(i, ch);
        nextStep.add(sb.toString());
        sb.deleteCharAt(i);
      }
    }
    return getPermutationsRec(s.substring(1), nextStep);
  }

  public int factorial(int n) {
    if (n > 10) {
      throw new IllegalArgumentException("Max 10 allowed - that's already 37 megabytes of strings.");
    }
    int fact = 1;
    for (int i = 2; i <= n; i++) {
      fact = fact * i;
    }
    return fact;
  }

  // sorted compare: O(n log n) for sorting, then O(n) for checking
  // O(2n) = O(n) space
  public boolean isPermutation_sorting(String first, String second) {
    // should null be a permutation of null?
    if (Objects.equals(first, second)) {
      return true;
    }
    if (commonChecksFailed(first, second)) {
      return false;
    }

    char[] firstChars = first.toCharArray();
    char[] secondChars = second.toCharArray();
    Arrays.sort(firstChars);
    Arrays.sort(secondChars);
    return Arrays.equals(firstChars, secondChars);
  }

  // Generates frequency map of letters in first and seconds and checks if the maps are equal
  public boolean isPermutation_freqMap(String first, String second) {
    // should null be a permutation of null?
    if (Objects.equals(first, second)) {
      return true;
    }
    if (commonChecksFailed(first, second)) {
      return false;
    }

    return getLetterFrequencyMap(first).equals(getLetterFrequencyMap(second));
  }

  private Map<Character, Integer> getLetterFrequencyMap(String word) {
    Map<Character, Integer> freqMap = new HashMap<>(word.length());
    for (int i = 0; i < word.length(); i++) {
      char key = word.charAt(i);
      Integer count = freqMap.computeIfAbsent(key, k -> 0);
      freqMap.put(key, count + 1);
    }
    return freqMap;
  }

  private boolean commonChecksFailed(String first, String second) {
    if (first == null || second == null) {
      return true;
    }
    return first.length() != second.length();
  }

  public static void main(String[] args) {
    CheckPermutationCh1_02 sut = new CheckPermutationCh1_02();

    TestRunner testRunner = new TestRunner();
    System.err.println("\nGenerating actual permutations (iterative)");
    System.err.println("------------------------------------------");
    runTests(testRunner, false, (s1, s2) -> sut.isPermutation_bruteForceGen(s1, s2, sut::getPermutationsIter));
    System.err.println("\nGenerating actual permutations (recursive)");
    System.err.println("------------------------------------------");
    runTests(testRunner, false, (s1, s2) -> sut.isPermutation_bruteForceGen(s1, s2, sut::getPermutationsRecursive));
    System.err.println("\nSorted");
    System.err.println("------------------------------------------");
    runTests(testRunner, true, sut::isPermutation_sorting);
    System.err.println("\nUsing letter frequency maps");
    System.err.println("------------------------------------------");
    runTests(testRunner, true, sut::isPermutation_freqMap);
    System.err.printf("\n%s tests run, %s failed\n", testRunner.getTotalCount(), testRunner.getFailedCount());
  }

  private static void runTests(TestRunner tr, boolean allowLongStrings, BiFunction<String, String, Boolean> code) {
    tr.assertBoolean("null is a permutation of null", () -> code.apply(null, null), true);
    tr.assertBoolean("null is not a permutation of empty", () -> code.apply(null, ""), false);
    tr.assertBoolean("empty is not a permutation of null", () -> code.apply("", null), false);
    tr.assertBoolean("null is not a permutation of a", () -> code.apply(null, "a"), false);
    tr.assertBoolean("a is not a permutation of null", () -> code.apply("a", null), false);
    tr.assertBoolean("empty string is a permutation of another empty", () -> code.apply("", ""), true);
    tr.assertBoolean("string is a permutation of string", () -> code.apply("string", "string"), true);
    tr.assertBoolean("abc is a permutation of bca", () -> code.apply("abc", "bca"), true);
    tr.assertBoolean("kayak is a permutation of yaakk", () -> code.apply("kayak", "yaakk"), true);
    tr.assertBoolean("abc is not a permutation of abcd", () -> code.apply("abc", "abcd"), false);
    tr.assertBoolean("1234567890 is a permutation of 0246813579", () -> code.apply("1234567890", "0246813579"), true);
    tr.assertBoolean("890 is a permutation of 089", () -> code.apply("890", "089"), true);
    if (allowLongStrings) {
      tr.assertBoolean("'golden retriever' is a permutation of retrie'v golden'er",
          () -> code.apply("'golden retriever'", "retrie'v golden'er"), true);
    }
  }
}
