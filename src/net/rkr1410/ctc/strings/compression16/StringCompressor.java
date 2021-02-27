package net.rkr1410.ctc.strings.compression16;

public interface StringCompressor {
  String compress(String s);
  int getCompressedSize(String s);
}
