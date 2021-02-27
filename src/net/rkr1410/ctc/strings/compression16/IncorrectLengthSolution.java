package net.rkr1410.ctc.strings.compression16;

public class IncorrectLengthSolution implements StringCompressor {
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
    return 1; // wrong
  }
}
