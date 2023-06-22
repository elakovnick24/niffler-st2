package guru.qa.niffler.test.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage{
    private final SelenideElement username = $("input[name='username']");
    private final SelenideElement tapLoginForm = $("a[href*='redirect']");
    private final SelenideElement password = $("input[name='password']");
    private final SelenideElement loginButton = $("button[type='submit']");

    @Override
    public BasePage checkThatPageLoaded() {
        return this;
    }
    public void login(UserJson user) {
        tapLoginForm.click();
        username.setValue(user.getUsername());
        password.setValue(user.getPassword());
        loginButton.click();
    }

    public void login(String name, String setPassword) {
        tapLoginForm.click();
        username.setValue(String.valueOf(name));
        password.setValue(setPassword);
        loginButton.click();
    }

}
