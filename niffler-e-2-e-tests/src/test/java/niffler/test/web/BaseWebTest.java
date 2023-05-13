package niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import niffler.jupiter.annotation.WebTest;
import niffler.test.page.FriendsPage;
import niffler.test.page.LoginPage;
import niffler.test.page.MainPage;
import niffler.test.page.ProfilePage;
import niffler.test.page.component.HeaderNavigationComponent;
import org.junit.jupiter.api.AfterAll;

@WebTest
public abstract class BaseWebTest {
  static {
    Configuration.browserSize = "1920x1080";
    Configuration.browser = "edge";
  }

  LoginPage loginPage = new LoginPage();

  MainPage mainPage = new MainPage();
  FriendsPage friendsPage = new FriendsPage();
  ProfilePage profilePage = new ProfilePage();
  HeaderNavigationComponent headerNavigation = new HeaderNavigationComponent();

  @AfterAll
  public static void afterAll() {
    Selenide.closeWebDriver();
  }
}
