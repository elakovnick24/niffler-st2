package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.test.page.RegistrationPage;
import org.junit.jupiter.api.Test;

public class RegistrationWebTest extends BaseWebTest {

    @Test
    void errorMessageShouldBeVisibleInCaseThatPasswordAreDifferent() {
        Selenide.open(RegistrationPage.URL, RegistrationPage.class)
                .checkThatPageLoaded()
                .fillRegistrationForm("user", "123", "12345")
                .checkErrorMessage("Passwords should be equal");
    }
}
