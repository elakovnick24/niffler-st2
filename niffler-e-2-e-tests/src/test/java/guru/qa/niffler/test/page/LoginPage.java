package guru.qa.niffler.test.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.UserJson;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage {

    public static final String URL = Config.getConfig().getAuthUrl() + "login";
    private final SelenideElement header = $(".form__paragraph");
    private final SelenideElement username = $("input[name='username']");
    private final SelenideElement password = $("input[name='password']");
    private final SelenideElement loginButton = $("button[type='submit']");

    private final SelenideElement error = $(".form__error");

    private final SelenideElement footer = $(".form__paragraph:nth-child(8)");

    private final SelenideElement signUpBtn = $(byLinkText("Sign up!"));

    @Override
    public LoginPage checkThatPageLoaded() {
        header.shouldHave(text("Please sign in"));
        return this;
    }

    public MainPage fillLoginForm(UserJson user) {
        username.setValue(user.getUsername());
        password.setValue(user.getPassword());
        loginButton.click();
        return new MainPage();
    }

    public MainPage fillLoginForm(String name, String pass) {
        username.setValue(name);
        password.setValue(pass);
        loginButton.click();
        return new MainPage();
    }

    public LoginPage fillLoginFormError(String name, String pass) {
        username.setValue(name);
        password.setValue(pass);
        loginButton.click();
        return this;
    }
    public LoginPage loginBtnHasText() {
        loginButton.shouldHave(text("Sign In"));
        return this;
    }

    public LoginPage checkErrorMessage(String expectedMessage) {
        error.shouldHave(text(expectedMessage));
        return this;
    }

    public RegistrationPage openSignUpForm() {
        footer.shouldHave(text("Have no account? "));
        footer.shouldHave(text("Sign up!"));
        signUpBtn.click();
        return new RegistrationPage();
    }
}
