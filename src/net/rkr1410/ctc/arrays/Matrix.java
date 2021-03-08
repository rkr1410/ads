package net.rkr1410.ctc.arrays;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Matrix {
  public static int[][] createRandomMatrix(int columnCount, int rowCount, int minValue, int maxValue) {
    Random rand = new Random();
    int[][] matrix = new int[rowCount][];
    for (int row = 0; row < rowCount; row++) {
      matrix[row] = new int[columnCount];
      for (int col = 0; col < columnCount; col++) {
        matrix[row][col] = Math.abs(rand.nextInt() % maxValue) + minValue;
      }
    }
    return matrix;
  }

  public static int[][] createOrderedMatrix(int columnCount, int rowCount) {
    int num = 1;
    int[][] matrix = new int[rowCount][];
    for (int row = 0; row < rowCount; row++) {
      matrix[row] = new int[columnCount];
      for (int col = 0; col < columnCount; col++) {
        matrix[row][col] = num++;
      }
    }
    return matrix;
  }


  public static void printMatrix(int[][] matrix) {
    if (matrix == null) {
      throw new NullPointerException("matrix can't be null");
    }
    for (int[] row : matrix) {
      if (row == null) {
        throw new NullPointerException("matrix can't have null rows");
      }
    }
    OptionalInt maybeMax = Arrays.stream(matrix).flatMapToInt(Arrays::stream).max();
    if (!maybeMax.isPresent()) {
      System.out.println("|matrix empty|");
      return;
    }

    int max = maybeMax.getAsInt();
    int len = digitCount(max);

    for (int[] row : matrix) {
      System.out.println(format(Arrays.stream(row).mapToObj(Integer::toString), len));
    }
  }

  private static String format(Stream<String> stream, int maxlength) {
    return stream.collect(
        () -> new Object() {
          final StringJoiner stringJoiner = new StringJoiner(",");
          int count;

          @Override
          public String toString() {
            return "|" + stringJoiner + " |";
          }
        },
        (container, currentString) -> {
          container.stringJoiner.add(String.format("%" + (maxlength + 1) + "s", currentString));
          container.count++;
        },
        (accumulatingContainer, currentContainer) -> {
          accumulatingContainer.stringJoiner.merge(currentContainer.stringJoiner);
          accumulatingContainer.count += currentContainer.count;
        }
    ).toString();
  }

  private static int digitCount(int number) {
    int digitCount = 1;
    while ((number = number / 10) != 0) digitCount++;
    return digitCount;
  }
}
