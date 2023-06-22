package guru.qa.niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.test.page.*;
import guru.qa.niffler.test.page.component.Header;
import org.junit.jupiter.api.AfterAll;

@WebTest
public abstract class BaseWebTest {
  static {
    Configuration.browserSize = "1920x1080";
    Configuration.browser = "chrome";
  }

  public LoginPage loginPage = new LoginPage();

  public MainPage mainPage = new MainPage();
  public FriendsPage friendsPage = new FriendsPage();
  public ProfilePage profilePage = new ProfilePage();
  public Header header = new Header();

  @AfterAll
  public static void afterAll() {
    Selenide.closeWebDriver();
  }

}
