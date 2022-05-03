package com.mygdx.pirategame.screens;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

public class SkillTreeTest {
  /** Method under test: {@link SkillTree#pointsCheck(int)} */
  @Test
  @Ignore("TODO: Complete this test")
  public void testPointsCheck() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
    //       at jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
    //       at jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
    //       at jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
    //       at java.util.Objects.checkIndex(Objects.java:359)
    //       at java.util.ArrayList.get(ArrayList.java:427)
    //       at com.mygdx.pirategame.screens.SkillTree.pointsCheck(SkillTree.java:64)
    //   In order to prevent pointsCheck(int)
    //   from throwing IndexOutOfBoundsException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   pointsCheck(int).
    //   See https://diff.blue/R013 to resolve this issue.

    SkillTree.pointsCheck(1);
  }

  /** Method under test: {@link SkillTree#pointsCheck(int)} */
  @org.junit.jupiter.api.Test
  @Disabled("TODO: Complete this test")
  void testPointsCheck2() {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
    //       at jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
    //       at jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
    //       at jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
    //       at java.util.Objects.checkIndex(Objects.java:359)
    //       at java.util.ArrayList.get(ArrayList.java:427)
    //       at com.mygdx.pirategame.screens.SkillTree.pointsCheck(SkillTree.java:64)
    //   In order to prevent pointsCheck(int)
    //   from throwing IndexOutOfBoundsException, add constructors or factory
    //   methods that make it easier to construct fully initialized objects used in
    //   pointsCheck(int).
    //   See https://diff.blue/R013 to resolve this issue.

    SkillTree.pointsCheck(1);
  }
}
