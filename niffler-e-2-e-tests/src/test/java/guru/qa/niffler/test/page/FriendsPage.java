package guru.qa.niffler.test.page;

import com.codeborne.selenide.ElementsCollection;
import guru.qa.niffler.test.page.component.Header;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FriendsPage extends BasePage {

    private final Header header = new Header();
    private final ElementsCollection tableOfFriends = $$(".table thead tr");

    @Override
    public FriendsPage checkThatPageLoaded() {
        tableOfFriends.shouldHave(texts("Avatar", "Username", "Name", "Actions"));
        return this;
    }

    public FriendsPage listFriendIsNotEmpty() {
        $$(".table tbody tr").shouldHave(sizeGreaterThan(0));
        return this;
    }

    public FriendsPage addFriend(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button")).click();
        return this;
    }

    public FriendsPage submitInvitation(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button")).click();
        return this;
    }

    public FriendsPage declineInvitation(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button")).click();
        return this;
    }

    public FriendsPage shouldHasActionAddFriend(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button")).click();
        return this;
    }

    public FriendsPage shouldHasActionSubmitAndDecline(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button")).click();
        return this;
    }

}
