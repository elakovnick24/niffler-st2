package niffler.test.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import niffler.test.page.FriendsPage;
import niffler.test.page.LoginPage;
import niffler.test.page.MainPage;
import niffler.test.page.ProfilePage;
import niffler.test.page.component.HeaderNavigationComponent;
import org.junit.jupiter.api.AfterAll;


public abstract class BaseWebTest {
  static {
    Configuration.browserSize = "1920x1080";
    Configuration.browser = "firefox";
  }

  public LoginPage loginPage = new LoginPage();

  public MainPage mainPage = new MainPage();
  public FriendsPage friendsPage = new FriendsPage();
  public ProfilePage profilePage = new ProfilePage();
  public HeaderNavigationComponent headerNavigation = new HeaderNavigationComponent();

  @AfterAll
  public static void afterAll() {
    Selenide.closeWebDriver();
  }

}
