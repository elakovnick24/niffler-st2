package niffler.test;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.db.entity.UserEntity;
import niffler.jupiter.extension.CreateUserDBExtension;
import niffler.test.web.BaseWebTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CreateUserDBExtension.class)
public class LoginNewUserTest extends BaseWebTest {

    @AllureId("104")
    @Test
    void loginTest(UserEntity user) {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
        loginPage.login(user.getUsername(), user.getPassword());
        headerNavigation
                .openFriends()
                .checkTextInHeader("Niffler. The coin keeper.");
    }

}

