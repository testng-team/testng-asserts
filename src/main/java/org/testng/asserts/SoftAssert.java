package org.testng.asserts;

import org.assertj.core.api.SoftAssertions;
import org.opentest4j.MultipleFailuresError;

/**
 * When an assertion fails, don't throw an exception but record the failure. Calling {@code
 * assertAll()} will cause an exception to be thrown if at least one assertion failed.
 *
 * <p>Failures are collected and aggregated by AssertJ's {@link SoftAssertions}, so {@code
 * assertAll()} reports them using AssertJ's native multiple-failures format.
 */
public class SoftAssert extends Assertion {
  private final SoftAssertions softly = new SoftAssertions();

  @Override
  protected void doAssert(IAssert<?> a) {
    onBeforeAssert(a);
    try {
      a.doAssert();
      onAssertSuccess(a);
    } catch (AssertionError ex) {
      onAssertFailure(a, ex);
      softly.collectAssertionError(ex);
    } finally {
      onAfterAssert(a);
    }
  }

  public void assertAll() {
    assertAll(null);
  }

  public void assertAll(String message) {
    try {
      softly.assertAll();
    } catch (AssertionError e) {
      // AssertJ keeps each failure in MultipleFailuresError#getFailures() but does not wire them
      // into the stack trace. Expose them (and their root causes) as suppressed exceptions so the
      // underlying failure details are still surfaced by standard tooling (see GITHUB-1778).
      if (e instanceof MultipleFailuresError) {
        for (Throwable failure : ((MultipleFailuresError) e).getFailures()) {
          e.addSuppressed(failure);
        }
      }
      if (message == null) {
        throw e;
      }
      throw new AssertionError(message + System.lineSeparator() + e.getMessage(), e);
    }
  }
}
