package net.rkr1410.ctc.strings.compression16;

public class CorrectLengthSolution implements StringCompressor {
  @Override
  public String compress(String s) {
    int count = 0;
    int length = getCompressedSize(s);
    StringBuilder retValBuilder = new StringBuilder(length);
    for (int i = 0; i < s.length(); i++) {
      count += 1;
      if (i == s.length() - 1 || s.charAt(i) != s.charAt(i + 1)) {
        retValBuilder.append(s.charAt(i)).append(count);
        count = 0;
      }
    }
    return retValBuilder.toString();
  }

  @Override
  public int getCompressedSize(String s) {
    int count = 0;
    int totalLength = 0;

    for (int i = 0; i < s.length(); i++) {
      count += 1;
      if (i == s.length() - 1 || s.charAt(i) != s.charAt(i + 1)) {
        totalLength += getDigitCount(count) + 1;
        count = 0;
      }
    }
    return totalLength;
  }

  private int getDigitCount(int x) {
    return x >= 1000000000 ? 10
        : x >= 100000000 ? 9
        : x >= 10000000 ? 8
        : x >= 1000000 ? 7
        : x >= 100000 ? 6
        : x >= 10000 ? 5
        : x >= 1000 ? 4
        : x >= 100 ? 3
        : x >= 10 ? 2
        : 1;
  }

  public static void main(String[] args) {
    System.err.println(Integer.MAX_VALUE);
  }
}
