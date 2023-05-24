package niffler.test.page;

import com.codeborne.selenide.SelenideElement;
import niffler.model.CategoryJson;
import niffler.model.SpendJson;
import niffler.model.UserJson;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement username = $("input[name='username']");
    private final SelenideElement tapLoginForm = $("a[href*='redirect']");
    private final SelenideElement password = $("input[name='password']");
    private final SelenideElement loginButton = $("button[type='submit']");

    public void login(UserJson user) {
        tapLoginForm.click();
        username.setValue(user.getUsername());
        password.setValue(user.getPassword());
        loginButton.click();
    }

    public void login(CategoryJson name, String setPassword) {
        tapLoginForm.click();
        username.setValue(String.valueOf(name));
        password.setValue(setPassword);
        loginButton.click();
    }

    public void login(SpendJson name, String setPassword) {
        tapLoginForm.click();
        username.setValue(String.valueOf(name));
        password.setValue(setPassword);
        loginButton.click();
    }

    public void login(String name, String setPassword) {
        tapLoginForm.click();
        username.setValue(String.valueOf(name));
        password.setValue(setPassword);
        loginButton.click();
    }
}
