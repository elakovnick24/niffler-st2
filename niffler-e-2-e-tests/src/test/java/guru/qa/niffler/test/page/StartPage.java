package guru.qa.niffler.test.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class StartPage extends BasePage {

    public static final String URL = Config.getConfig().getFrontUrl();

    private final SelenideElement header = $(".main__header");

    private final SelenideElement loginBtn = $(By.cssSelector("a.main__link[href='/redirect']"));
    private final SelenideElement registerBtn = $(By.cssSelector("a.main__link[href=\"http://127.0.0.1:9001/register\"]"));

    @Override
    public StartPage checkThatPageLoaded() {
        header.shouldHave(text("Welcome to magic journey with Niffler. The coin keeper"));
        return this;
    }

    public LoginPage openLoginForm() {
        loginBtn.shouldHave(text("Login"));
        loginBtn.click();
        return new LoginPage();
    }

    public RegistrationPage openRegisterForm() {
        registerBtn.shouldHave(text("Register"));
        registerBtn.click();
        return new RegistrationPage();
    }
}
