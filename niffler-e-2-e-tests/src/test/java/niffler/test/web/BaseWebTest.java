package niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import niffler.jupiter.annotation.WebTest;
import org.junit.jupiter.api.AfterAll;

@WebTest
public abstract class BaseWebTest {
  static {
    Configuration.browserSize = "1920x1080";
    Configuration.browser = "edge";
  }

  @AfterAll
  public static void afterAll() {
    Selenide.closeWebDriver();
  }
}
