package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.extension.UsersQueueExtension;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.test.page.FriendsPage;
import guru.qa.niffler.test.page.LoginPage;
import guru.qa.niffler.test.page.PeoplePage;
import guru.qa.niffler.test.page.component.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.User.UserType.INVITATION_SENT;
import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

@Disabled
@ExtendWith(UsersQueueExtension.class)
public class FriendWebTest extends BaseWebTest {
    LoginPage loginPage = new LoginPage();
    PeoplePage peoplePage = new PeoplePage();
    FriendsPage friendsPage = new FriendsPage();
    Header headerNavigation = new Header();

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
        loginPage.fillLoginForm(user1);
        headerNavigation.openPeople();
        peoplePage.findInvitationPending();
    }
}
