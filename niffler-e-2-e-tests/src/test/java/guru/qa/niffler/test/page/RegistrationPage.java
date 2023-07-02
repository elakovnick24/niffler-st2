package guru.qa.niffler.test.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.UserJson;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage extends BasePage<RegistrationPage> {

    public static final String URL = Config.getConfig().getFrontUrl() + "/register";
    // not use static in this case
    private final SelenideElement header = $(".form__paragraph");
    //    private static final String headerSelector = ".form__header";
    //    private static final By byHeaderSelector  = new ByCssSelector(".form__header");
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement passwordSubmitInput = $("#passwordSubmit");
    private final SelenideElement signUpBtn = $("button[type='submit']");
    private final SelenideElement error = $(".form__error");
    private final SelenideElement loginBtn = $(By.linkText("Sign in!"));


    @Override
    public RegistrationPage checkThatPageLoaded() {
        header.shouldHave(text("Registration form"));
        return this;
    }

    public LoginPage fillRegistrationForm(String username, String password, String passwordSubmit) {
        usernameInput.val(username);
        passwordInput.val(password);
        passwordSubmitInput.val(passwordSubmit);
        signUpBtn.click();
        header.shouldHave(text("Congratulations! You've registered!"));
        loginBtn.click();
        return new LoginPage();
    }

    public RegistrationPage fillRegistrationFormError(String username, String password, String passwordSubmit) {
        usernameInput.val(username);
        passwordInput.val(password);
        passwordSubmitInput.val(passwordSubmit);
        signUpBtn.click();
        return this;
    }

    public RegistrationPage checkErrorMessage(String expectedMessage) {
        error.shouldHave(text(expectedMessage));
        return this;
    }
}
