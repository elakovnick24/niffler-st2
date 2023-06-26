package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.ClassPathUser;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.test.page.LoginPage;
import guru.qa.niffler.test.page.StartPage;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LoginWebTest extends BaseWebTest {

    @ValueSource(strings = {
            "testdata/dima.json",
            "testdata/emma.json"
    })
    @AllureId("104")
    @ParameterizedTest
    void successfullLoginTest(@ClassPathUser UserJson user) {
        Selenide.open(StartPage.URL, StartPage.class)
                .checkThatPageLoaded()
                .openLoginForm()
                .checkThatPageLoaded()
                .fillLoginForm(user)
                .checkThatPageLoaded();
    }

    @Test
    void loginButnHasTextSignIn() {
        Selenide.open(LoginPage.URL, LoginPage.class);
        loginPage
                .checkThatPageLoaded()
                .loginBtnHasText();
    }

    @Test
    void redirectToSignUpForm() {
        Selenide.open(LoginPage.URL, LoginPage.class);
        loginPage
                .checkThatPageLoaded()
                .openSignUpForm()
                .checkThatPageLoaded();
    }

    @Test
    public void errorMessageShouldBeVisibleInCaseThatUsernameLessThan3Symbols() {
        Selenide.open(LoginPage.URL, LoginPage.class);
        loginPage
                .checkThatPageLoaded()
                .fillLoginFormError("a", "aaaa")
                .checkErrorMessage("Bad credentials");
    }

}
