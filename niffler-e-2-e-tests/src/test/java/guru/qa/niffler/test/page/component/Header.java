package guru.qa.niffler.test.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.test.page.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class Header extends BaseComponent<Header> {
    private final SelenideElement header = $(".header");
//    private final SelenideElement headerTitle = $(".header__title");
    private final SelenideElement mainPageBtn = $(".header__navigation-item img[src*='main']");
    private final SelenideElement friendsPageBtn = $("a.header__link[href*='friends']");
    private final SelenideElement peoplePageBtn = $(".header__navigation-item img[src*='globe']");
    private final SelenideElement profilePageBtn = $("a[href*='profile']");
    private final SelenideElement logOutBtn = $(".header__logout .button-icon_type_logout");

    public Header() {
        super($(".header"));
    }

    @Override
    public Header checkThatComponentDisplayer() {
        self.$(".header__title").shouldHave(text("Niffler. The coin keeper."));
        return this;
    }

    public MainPage openMain() {
        mainPageBtn.click();
        return new MainPage();
    }

    public FriendsPage openFriends() {
        friendsPageBtn.click();
        return new FriendsPage();
    }

    public PeoplePage openPeople() {
        peoplePageBtn.click();
        return new PeoplePage();
    }

    public ProfilePage openProfile() {
        profilePageBtn.click();
        return new ProfilePage();
    }

    public StartPage logout() {
        logOutBtn.click();
        return new StartPage();
    }

}
