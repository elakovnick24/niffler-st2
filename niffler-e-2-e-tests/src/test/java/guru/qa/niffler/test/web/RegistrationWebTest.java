package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import guru.qa.niffler.test.page.RegistrationPage;
import guru.qa.niffler.test.page.StartPage;
import org.junit.jupiter.api.Test;

public class RegistrationWebTest extends BaseWebTest {

    Faker faker = new Faker();
    @Test
    void successRegistration() {
        Selenide.open(StartPage.URL, StartPage.class)
                .checkThatPageLoaded()
                .openRegisterForm()
                .checkThatPageLoaded()
                .fillRegistrationForm(String.valueOf(faker.name()), "12345", "12345")
                .checkThatPageLoaded();
    }
    @Test
    public void errorMessageShouldBeVisibleInCaseThatPasswordAreDifferent() {
        Selenide.open(RegistrationPage.URL, RegistrationPage.class)
                .checkThatPageLoaded()
                .fillRegistrationFormError("user", "123", "12345")
                .checkErrorMessage("Passwords should be equal");
    }

    @Test
    public void errorMessageShouldBeVisibleInCaseThatUsernameNotUniq() {
        final String username = "dima";

        Selenide.open(RegistrationPage.URL, RegistrationPage.class)
                .checkThatPageLoaded()
                .fillRegistrationFormError(username, "12345", "12345")
                .checkErrorMessage("Username `" + username + "` already exists");
    }

    @Test
    public void errorMessageShouldBeVisibleInCaseThatPasswordLessThan3Symbols() {
        Selenide.open(RegistrationPage.URL, RegistrationPage.class)
                .checkThatPageLoaded()
                .fillRegistrationFormError("tester", "1", "1")
                .checkErrorMessage("Allowed password length should be from 3 to 12 characters");
    }

    @Test
    public void errorMessageShouldBeVisibleInCaseThatUsernameLessThan3Symbols() {
        Selenide.open(RegistrationPage.URL, RegistrationPage.class)
                .checkThatPageLoaded()
                .fillRegistrationFormError("a", "12345", "12345")
                .checkErrorMessage("Allowed username length should be from 3 to 50 characters");
    }
}
