package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.ClassPathUser;
import niffler.model.UserJson;
import niffler.test.page.LoginPage;
import niffler.test.page.component.HeaderNavigationComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class LoginWebTest extends BaseWebTest {
    LoginPage loginPage = new LoginPage();
    HeaderNavigationComponent headerNavigationComponent = new HeaderNavigationComponent();

    @BeforeEach
    void doLogin() {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
    }
    @ValueSource(strings = {
            "testdata/dima.json",
            "testdata/emma.json"
    })
    @AllureId("104")
    @ParameterizedTest
    void loginTest(@ClassPathUser UserJson user) throws IOException {
        loginPage.login(user);
        headerNavigationComponent
                .openFriends()
                .checkTextInHeader("Niffler. The coin keeper.");
    }

}
