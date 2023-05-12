package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.ClassPathUser;
import niffler.jupiter.annotation.User;
import niffler.jupiter.extension.UsersQueueExtension;
import niffler.model.UserJson;
import niffler.page.FriendsPage;
import niffler.page.LoginPage;
import niffler.page.PeoplePage;
import niffler.page.component.HeaderNavigationComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

@ExtendWith(UsersQueueExtension.class)
public class FriendWebTest extends BaseWebTest {
    LoginPage loginPage = new LoginPage();
    PeoplePage peoplePage = new PeoplePage();
    FriendsPage friendsPage = new FriendsPage();
    HeaderNavigationComponent headerNavigation = new HeaderNavigationComponent();

    @ParameterizedTest
    @BeforeEach
    public void openMainPage(@ClassPathUser UserJson user) {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
        loginPage.login(user);
    }

    @AllureId("102")
    @Test
    void friendsShouldBeVisible0(@User(userType = User.UserType.WITH_FRIENDS) UserJson user1) {
        headerNavigation.openFriends();
        friendsPage.listFriendIsNotEmpty();
    }
    @AllureId("103")
    @Test
    //TODO: Доработать parameterResolver
    void friendsShouldBeVisible1(
            @User(userType = User.UserType.INVITATION_SENT) UserJson user1,
            @User(userType = User.UserType.WITH_FRIENDS) UserJson user2) {
        Selenide.open("http://127.0.0.1:3000/main");
        loginPage.login(user1);
        peoplePage.findInvitationPending();

    }

}
