package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.entity.UserEntity;
import guru.qa.niffler.jupiter.annotation.GenerateUserWith;
import guru.qa.niffler.jupiter.extension.CreateUserDBExtension;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.annotation.GenerateUserWith.ClientDB.HIBERNATE;

@Disabled
@ExtendWith(CreateUserDBExtension.class)
public class LoginNewUserWithExtensionTest extends BaseWebTest {

    @AllureId("105")
    @Test
    void loginTest(@GenerateUserWith(
            clientDB = HIBERNATE
    ) UserEntity user) {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
        loginPage.login(user.getUsername(), user.getPassword());
        header
                .openFriends()
                .checkThatPageLoaded();
    }
}
