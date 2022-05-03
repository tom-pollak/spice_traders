package com.mygdx.pirategame.screens;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;

class GameScreenTest {
    /**
     * Method under test: {@link GameScreen#changeDamage(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testChangeDamage() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke
        // "com.mygdx.pirategame.sprites.College.changeDamageReceived(int)" because the return value of
        // "com.diffblue.cover.agent.readwrite.RuntimeWrappers.map$get(java.util.Map, Object)" is null
        //       at com.mygdx.pirategame.screens.GameScreen.changeDamage(GameScreen.java:217)
        //   In order to prevent changeDamage(int)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   changeDamage(int).
        //   See https://diff.blue/R013 to resolve this issue.

        GameScreen.changeDamage(42);
    }

    /**
     * Method under test: {@link GameScreen#changeAcceleration(Float)}
     */
    @Test
    void testChangeAcceleration() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by changeAcceleration(Float)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        GameScreen.changeAcceleration(10.0f);
    }

    /**
     * Method under test: {@link GameScreen#changeMaxSpeed(Float)}
     */
    @Test
    void testChangeMaxSpeed() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by changeMaxSpeed(Float)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        GameScreen.changeMaxSpeed(10.0f);
    }
}
