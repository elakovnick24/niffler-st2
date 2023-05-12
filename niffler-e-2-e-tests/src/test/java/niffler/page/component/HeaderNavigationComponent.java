package niffler.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import niffler.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;

public class HeaderNavigationComponent {
    private final SelenideElement header = $(".header");
    private final SelenideElement mainTab = $(".header__navigation-item img[src*='main']");
    private final SelenideElement friendsTab = $("a[href*='friends']");
    private final SelenideElement peoplesTab = $(".header__navigation-item img[src*='globe']");
    private final SelenideElement profileTab = $("a[href*='profile']");
    private final SelenideElement logoutButton = $(".header__logout .button-icon_type_logout");

    public HeaderNavigationComponent openMain() {
        peoplesTab.click();
        return this;
    }

    public HeaderNavigationComponent openFriends() {
        friendsTab.click();
        return this;
    }

    public HeaderNavigationComponent openPeople() {
        peoplesTab.click();
        return this;
    }

    public HeaderNavigationComponent openProfile() {
        profileTab.click();
        return this;
    }

    public HeaderNavigationComponent logout() {
        logoutButton.click();
        return this;
    }

    public HeaderNavigationComponent checkTextInHeader(String expectedText) {
        header.should(Condition.visible).should(Condition.text(expectedText));
        return this;
    }
}
