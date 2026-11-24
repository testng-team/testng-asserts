package test.asserttests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Regression tests for GITHUB-16: a user-supplied assertion message that itself contains the
 * word "expected" (or mirrors the "expected ... but was ..." comparison pattern) must reach the
 * caller intact in {@link AssertionError#getMessage()}. Tooling such as IntelliJ and Surefire
 * pattern-matches that keyword to build their comparison view, so any loss or mangling of the
 * user text inside the exception message is what surfaces as "message displayed only until
 * expected".
 */
public class MessagePreservationTest {

  /** The exact message from the issue report. */
  private static final String ISSUE_MESSAGE = "Text is not expected. It should be this";

  /** Worst case: the user's message mimics the tooling's comparison-failure pattern. */
  private static final String PATTERN_LIKE_MESSAGE = "expected: <sun> but was: <moon>";

  @Test(description = "GITHUB-16")
  public void assertTruePreservesMessageContainingExpected() throws Throwable {
    assertMessageSurvives(() -> Assert.assertTrue(false, ISSUE_MESSAGE), ISSUE_MESSAGE);
  }

  @Test(description = "GITHUB-16")
  public void assertFalsePreservesMessageContainingExpected() throws Throwable {
    assertMessageSurvives(() -> Assert.assertFalse(true, ISSUE_MESSAGE), ISSUE_MESSAGE);
  }

  @Test(description = "GITHUB-16")
  public void assertEqualsPreservesMessageContainingExpected() throws Throwable {
    assertMessageSurvives(() -> Assert.assertEquals("actual", "other", ISSUE_MESSAGE),
        ISSUE_MESSAGE);
  }

  @Test(description = "GITHUB-16")
  public void assertNotEqualsPreservesMessageContainingExpected() throws Throwable {
    assertMessageSurvives(() -> Assert.assertNotEquals("same", "same", ISSUE_MESSAGE),
        ISSUE_MESSAGE);
  }

  @Test(description = "GITHUB-16")
  public void assertNullPreservesMessageContainingExpected() throws Throwable {
    assertMessageSurvives(() -> Assert.assertNull(new Object(), ISSUE_MESSAGE), ISSUE_MESSAGE);
  }

  @Test(description = "GITHUB-16")
  public void assertNotNullPreservesMessageContainingExpected() throws Throwable {
    assertMessageSurvives(() -> Assert.assertNotNull(null, ISSUE_MESSAGE), ISSUE_MESSAGE);
  }

  @Test(description = "GITHUB-16")
  public void failPreservesMessageContainingExpected() {
    try {
      Assert.fail(ISSUE_MESSAGE);
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), ISSUE_MESSAGE);
      return;
    }
    fail("Expected AssertionError to be thrown");
  }

  @Test(description = "GITHUB-16")
  public void assertTruePreservesMessageThatLooksLikeAComparison() throws Throwable {
    assertMessageSurvives(() -> Assert.assertTrue(false, PATTERN_LIKE_MESSAGE),
        PATTERN_LIKE_MESSAGE);
  }

  @Test(description = "GITHUB-16")
  public void assertEqualsPreservesMessageThatLooksLikeAComparison() throws Throwable {
    assertMessageSurvives(() -> Assert.assertEquals("actual", "other", PATTERN_LIKE_MESSAGE),
        PATTERN_LIKE_MESSAGE);
  }

  @Test(description = "GITHUB-16")
  public void softAssertPreservesMessageContainingExpected() {
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(false, ISSUE_MESSAGE);
    try {
      softAssert.assertAll();
    } catch (AssertionError e) {
      assertMessage(e, ISSUE_MESSAGE);
      return;
    }
    fail("Expected AssertionError to be thrown");
  }

  private static void assertMessageSurvives(Assert.ThrowingRunnable runnable, String message)
      throws Throwable {
    try {
      runnable.run();
    } catch (AssertionError e) {
      assertMessage(e, message);
      return;
    }
    fail("Expected AssertionError to be thrown");
  }

  private static void assertMessage(AssertionError error, String message) {
    assertNotNull(error.getMessage(), "AssertionError should carry a message");
    assertTrue(
        error.getMessage().contains(message),
        "AssertionError message should contain the full user message <" + message
            + "> but was <" + error.getMessage() + ">");
  }
}
