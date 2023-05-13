package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.model.UserJson;
import niffler.test.page.FriendsPage;
import niffler.test.page.LoginPage;
import niffler.test.page.PeoplePage;
import niffler.test.page.component.HeaderNavigationComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static niffler.jupiter.annotation.User.UserType.INVITATION_SENT;
import static niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

public class FriendWebTest extends BaseWebTest {
    LoginPage loginPage = new LoginPage();
    PeoplePage peoplePage = new PeoplePage();
    FriendsPage friendsPage = new FriendsPage();
    HeaderNavigationComponent headerNavigation = new HeaderNavigationComponent();

    @ParameterizedTest
    @BeforeEach
    public void openMainPage() {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
    }

    @AllureId("102")
    @Test
    void friendsShouldBeVisible0(@User(userType = WITH_FRIENDS) UserJson user1) {
        headerNavigation.openFriends();
        friendsPage.listFriendIsNotEmpty();
    }
    @AllureId("103")
    @Test
    void friendsShouldBeVisible1(
            @User(userType = INVITATION_SENT) UserJson user1,
            @User(userType = WITH_FRIENDS) UserJson user2) {
        Selenide.open("http://127.0.0.1:3000/main");
        loginPage.login(user1);
        headerNavigation.openPeople();
        peoplePage.findInvitationPending();

    }

}
