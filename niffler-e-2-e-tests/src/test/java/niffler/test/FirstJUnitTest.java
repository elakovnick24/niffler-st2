package niffler.test;

import niffler.jupiter.extension.CallBackExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CallBackExtension.class)
public class FirstJUnitTest extends BaseTest{

    @AfterAll
    static void afterAll() {
        System.out.println("     #### @AfterAll");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("    #### @beforeAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("            #### @BeforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("            #### @AfterEach");
    }

    @Test
    void firstTest() {
        System.out.println("                        #### @Test firstTest()");
    }

    @Test
    void secondTest() {
        System.out.println("                        #### @Test secondTest()");
    }
}

