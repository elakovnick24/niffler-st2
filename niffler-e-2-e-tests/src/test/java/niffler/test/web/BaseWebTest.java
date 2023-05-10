package niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import niffler.test.BaseTest;
import org.junit.jupiter.api.AfterAll;

public class BaseWebTest extends BaseTest {

    static {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "http://127.0.0.1:3000";
    }

    @AfterAll
    static void afterAll() {
        Selenide.closeWebDriver();
    }
}
