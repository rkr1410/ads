package net.rkr1410.ctc.arrays;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

//   Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4
//    bytes, write a method to rotate the image by 90 degrees. (an you do this in place?
//    Hints: #51, #100
public class RotateMatrixCh01_07 {



  public void rotate(int[][] matrix) {
    Objects.requireNonNull(matrix, "matrix can't be null");
    if (matrix.length == 0) {
      return;
    }
    checkSquare(matrix);
    int tmp;

    // If the matrix length is odd, we don't need to rotate the innermost square, as it's just
    // a single number. So we can calculate number of "rings" to rotate with length /2
    int layerCount = matrix.length / 2;

    for (int layer = 0; layer < layerCount; layer++) {
      int layerLen = matrix.length - (2 * layer);
      for (int i = 0; i < layerLen - 1; i++) {
        int min = layer;
        int max = layer + layerLen - 1;
        int growing = min + i;
        int shrinking = max - i;

        // copy top row to tmp
        tmp = matrix[min][growing];
        // copy left column to top row
        matrix[min][growing] = matrix[shrinking][min];
        // copy bottom row to left column
        matrix[shrinking][min] = matrix[max][shrinking];
        // copy right column to bottom row
        matrix[max][shrinking] = matrix[growing][max];
        // copy saved top row to right column
        matrix[growing][max] = tmp;
      }
    }
  }

  private void checkSquare(int[][] matrix) {
    int len = matrix.length;

    for (int[] row : matrix) {
      if (row == null || row.length != len) {
        throw new IllegalArgumentException("Matrix must be square");
      }
    }
  }

  public static void main(String[] args) {
    int[][] m = Matrix.createOrderedMatrix(5, 5);
    Matrix.printMatrix(m);

    int len = m.length;

// 90 deg right + mirror image
//    for (int x = 0; x < len; x++) {
//      for (int y = 0; y < len; y++) {
//        System.err.print(" " + m[y][x]);
//      }
//      System.err.println();
//    }


// rotate left view
//    for (int x = 0; x < len; x++) {
//      for (int y = 0; y < len; y++) {
//        System.err.print(" " + m[y][len-x-1]);
//      }
//      System.err.println();
//    }

// rotate right view
//    for (int x = 0; x < len; x++) {
//      for (int y = 0; y < len; y++) {
//        System.err.print(" " + m[len-y-1][x]);
//      }
//      System.err.println();
//    }

    new RotateMatrixCh01_07().rotate(m);
    System.out.println();
    Matrix.printMatrix(m);
  }
}
