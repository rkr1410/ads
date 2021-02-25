package net.rkr1410.ctc.strings;

// Replace all spaces on char array with %20. There is just enough space at the end
public class URLifyCh1_03 {

  // move continuously
  private void moveByChar(char[] arr) {
    int appendIndex = arr.length - 1;

    for (int index = findLastWordEnd(arr); index >= 0; index--) {
      if (arr[index] == ' ') {
        arr[appendIndex--] = '0';
        arr[appendIndex--] = '2';
        arr[appendIndex--] = '%';
      } else {
        arr[appendIndex--] = arr[index];
      }
    }
  }

  private int findLastWordEnd(char[] arr) {
    for (int i = arr.length - 1; i >= 0; i--) {
      if (arr[i] != ' ') {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    char[] arr = " 123456 444 123123A   X            ".toCharArray();

    new URLifyCh1_03().moveByChar(arr);
    System.err.println(String.valueOf(arr));
  }
}
