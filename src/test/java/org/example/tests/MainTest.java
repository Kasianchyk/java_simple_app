package org.example.tests;

import org.example.app.TestRail;
import org.example.extension.RootExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.RegisterExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTest {

    @RegisterExtension
    RootExtension rootExtension = new RootExtension();

    @TestRail
    @Test
    @DisplayName("Test case 1")
    public void firstTest(){
        Assertions.assertEquals(1, 1);
    }

    @TestRail
    @Test
    @DisplayName("Test case 2")
    public void secondTest(){
        Assertions.assertEquals(1, 0);
    }

    @TestRail
    @Test
    @DisplayName("Test case 3")
    public void thirdTest(){
        Assertions.assertEquals(1, 1);
    }
}
