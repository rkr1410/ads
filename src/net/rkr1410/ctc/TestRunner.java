package net.rkr1410.ctc;

import java.util.Objects;
import java.util.function.Supplier;

public class TestRunner {
  int totalCount = 0;
  int failedCount = 0;

  public void assertBoolean(
      String testDescription,
      Supplier<Boolean> code,
      boolean expected) {
    totalCount++;
    System.err.print("\"" + testDescription + "\": ");
    try {
      boolean result = code.get();
      boolean success = result == expected;
      failedCount += success ? 0 : 1;
      System.err.println(success ? "passed" : "failed");
    } catch (Exception ex) {
      failedCount++;
      System.err.println("Failed with "
          + ex.getClass()
          + ", with a message: "
          + ex.getMessage());
    }
  }

  public void assertThrows(
      String testDescription,
      Supplier<Boolean> code,
      Class<? extends Throwable> expectedClass,
      String expectedMessage) {
    totalCount++;
    System.err.print("\"" + testDescription + "\": ");
    try {
      code.get();
      failedCount++;
      System.err.println("failed, expected an exception, but none thrown");
    } catch (Exception ex) {
      if (ex.getClass().equals(expectedClass)
          && Objects.equals(ex.getMessage(), expectedMessage)) {
        System.err.println("passed");
      } else {
        failedCount++;
        System.err.println("failed. Expected a "
            + expectedClass.getName()
            + " with message:\n\""
            + expectedMessage
            + "\"\nbut got a "
            + ex.getClass().getName()
            + " with message:\n"
            + getExceptionMessage(ex));
      }
    }
  }

  public int getTotalCount() {
    return totalCount;
  }

  public int getFailedCount() {
    return failedCount;
  }

  private String getExceptionMessage(Exception ex) {
    String message = ex.getMessage();
    if (message == null) {
      return "null";
    } else {
      return "\"" + message + "\"";
    }
  }
}

