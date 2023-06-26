package guru.qa.niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.test.page.*;
import guru.qa.niffler.test.page.component.Header;
import org.junit.jupiter.api.AfterEach;

@WebTest
public abstract class BaseWebTest {
  static {
    Configuration.browserSize = "1920x1080";
    Configuration.browser = "chrome";
  }

  protected LoginPage loginPage = new LoginPage();

  protected MainPage mainPage = new MainPage();
  protected FriendsPage friendsPage = new FriendsPage();
  protected ProfilePage profilePage = new ProfilePage();
  protected Header header = new Header();
  protected RegistrationPage registrationPage = new RegistrationPage();

  protected StartPage startPage = new StartPage();

  @AfterEach
  public void afterEach() {
    Selenide.closeWebDriver();
  }


}
