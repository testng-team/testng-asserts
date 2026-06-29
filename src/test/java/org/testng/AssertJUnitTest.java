package org.testng;

import org.testng.annotations.Test;

@SuppressWarnings({"deprecation", "removal"})
public class AssertJUnitTest {

  @Test
  public void assertEqualsPasses() {
    AssertJUnit.assertEquals(2, 1 + 1);
  }

  @Test
  public void assertArrayEqualsPasses() {
    AssertJUnit.assertArrayEquals(new int[] {1, 2, 3}, new int[] {1, 2, 3});
  }

  @Test(expectedExceptions = AssertionError.class)
  public void assertEqualsFails() {
    AssertJUnit.assertEquals(2, 3);
  }

  @Test(expectedExceptions = AssertionError.class)
  public void assertArrayEqualsFails() {
    AssertJUnit.assertArrayEquals(new int[] {1, 2, 3}, new int[] {1, 2, 4});
  }
}
