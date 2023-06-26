package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.test.page.StartPage;
import org.junit.jupiter.api.Test;

public class MainTest extends BaseWebTest {

    @Test
    void addNewSpendTest() {
        Selenide.open(StartPage.URL, StartPage.class);
        startPage
                .openLoginForm()
                .fillLoginForm("nick", "12345")
                .checkThatPageLoaded()
                .addNewSpend()
                .findFirstRowInSpendTableAndSelect("Relocate");
    }

    @Test
    void goToFriendsPageTest() {
        Selenide.open(StartPage.URL, StartPage.class);
        startPage
                .openLoginForm()
                .fillLoginForm("nick", "12345")
                .checkThatPageLoaded()
                .goToFriendPage()
                .checkThatPageLoaded();
    }

    @Test
    void goToPeoplePageTest() {
        Selenide.open(StartPage.URL, StartPage.class);
        startPage
                .openLoginForm()
                .fillLoginForm("nick", "12345")
                .checkThatPageLoaded()
                .goToPeoplePage()
                .checkThatPageLoaded();
    }

    @Test
    void goToProfilePageTest() {
        Selenide.open(StartPage.URL, StartPage.class);
        startPage
                .openLoginForm()
                .fillLoginForm("nick", "12345")
                .checkThatPageLoaded()
                .goToProfilePage()
                .checkThatPageLoaded();
    }

    @Test
    void logOutTest() {
        Selenide.open(StartPage.URL, StartPage.class);
        startPage
                .openLoginForm()
                .fillLoginForm("nick", "12345")
                .checkThatPageLoaded()
                .logOut()
                .checkThatPageLoaded();
    }

}
