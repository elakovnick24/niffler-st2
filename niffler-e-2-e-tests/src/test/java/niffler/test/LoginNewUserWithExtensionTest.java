package niffler.test;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.db.entity.UserEntity;
import niffler.jupiter.annotation.GenerateUserWith;
import niffler.jupiter.extension.CreateUserDBExtension;
import niffler.test.web.BaseWebTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static niffler.jupiter.annotation.GenerateUserWith.ClientDB.*;

@ExtendWith(CreateUserDBExtension.class)
public class LoginNewUserWithExtensionTest extends BaseWebTest {

    @AllureId("105")
    @Test
    void loginTest(@GenerateUserWith(
            clientDB = HIBERNATE,
            deleteUserAfterEach = false
    ) UserEntity user) {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
        loginPage.login(user.getUsername(), user.getPassword());
        headerNavigation
                .openFriends()
                .checkTextInHeader("Niffler. The coin keeper.");
    }
}
