package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.entity.UserEntity;
import guru.qa.niffler.jupiter.annotation.GenerateUserWith;
import guru.qa.niffler.jupiter.extension.CreateUserDBExtension;
import guru.qa.niffler.test.page.StartPage;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.GenerateUserWith.ClientDB.SPRING_JDBC;

@ExtendWith(CreateUserDBExtension.class)
public class LoginNewUserWithExtensionTest extends BaseWebTest {

    @AllureId("105")
    @Test
    void loginTest(@GenerateUserWith(
            clientDB = SPRING_JDBC) UserEntity user) {
        Selenide.open(StartPage.URL, StartPage.class)
                .checkThatPageLoaded()
                .openLoginForm()
                .checkThatPageLoaded()
                .fillLoginForm(user.getUsername(), user.getPassword())
                .checkThatPageLoaded()
                .goToFriendPage()
                .checkThatPageLoaded();
    }
}
