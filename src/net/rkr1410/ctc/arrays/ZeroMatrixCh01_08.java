package net.rkr1410.ctc.arrays;

import java.util.HashSet;
import java.util.Set;

// Write an algorithm such that if an element in an NxN matrix is 0, the entire row and column are set to 0
// Hints #17, #74, #102
public class ZeroMatrixCh01_08 {

  public static void main(String[] args) {
    int rows = 7, cols = 20;
    int[][] m = Matrix.createRandomMatrix(cols, rows, 0, 22);
    while (!hasAtLeastOneZero(m)) {
      m = Matrix.createRandomMatrix(cols, rows, 0, 22);
    }

    Matrix.printMatrix(m);
    System.out.println("------------------------------------------\n");

    int rowSize = m.length;
    int colSize = m[0].length;
    Set<Integer> zeroableCols = new HashSet<>(colSize);
    Set<Integer> zeroableRows = new HashSet<>(rowSize);
    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        if (m[row][col] == 0) {
          zeroableCols.add(col);
          zeroableRows.add(row);
        }
      }
    }

    System.err.println("rows " + zeroableRows);
    System.err.println("cols " + zeroableCols);

    for (int rowIndex : zeroableRows) {
      int[] row = m[rowIndex];
      for (int col = 0; col < colSize; col++) {
        row[col] = 0;
      }
    }

    for (int[] row : m) {
      for (int col : zeroableCols) {
        row[col] = 0;
      }
    }

    Matrix.printMatrix(m);
  }

  private static boolean hasAtLeastOneZero(int[][] matrix) {
    for (int[] row : matrix) {
      for (int element : row) {
        if (element == 0) {
          return true;
        }
      }
    }
    return false;
  }
}
