package net.rkr1410.ctc.strings.compression16;

import java.util.Objects;

//String Compression: Implement a method to perform basic string compression using the counts
//of repeated characters. For example, the string aabcccccaaa would become a2b1c5a3. If the
//"compressed" string would not become smaller than the original string, your method should return
//the original string. You can assume the string has only uppercase and lowercase letters (a - z).
//Hints: #92, #110
public class Test16 implements StringCompressor {

  private StringCompressor impl;

  private Test16(StringCompressor impl) {
    Objects.requireNonNull(impl, "implementation cannot be null);");
    this.impl = impl;
  }

  @Override
  public String compress(String s) {
    Objects.requireNonNull(s, "Compression candidate can't be null");
    if (s.length() <= 2 || getCompressedSize(s) >= s.length()) {
      return s;
    }
    return impl.compress(s);
  }

  @Override
  public int getCompressedSize(String s) {
    return impl.getCompressedSize(s);
  }

  public static void main(String[] args) {
    Test16 test = new Test16(new IncorrectLengthSolution());
    Test16 test2 = new Test16(new CorrectLengthSolution());

    //add one more s for the compressed length to exceed raw length
    String testString = "aaaabcd jumpsssssssssssssssssss overrrrr the lazy     doggo";
    System.err.println(test.compress(testString));
    System.err.println(test2.compress(testString));
  }
}
