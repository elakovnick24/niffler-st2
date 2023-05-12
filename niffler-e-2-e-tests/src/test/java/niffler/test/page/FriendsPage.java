package niffler.test.page;

import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FriendsPage {

    public FriendsPage addFriend(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button")).click();
        return this;
    }

//    public FriendsPage submitInvitation(String username) {
//        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
//                + username + "')]//following-sibling::td//button")).click();
//        return this;
//    }

//    public FriendsPage declineInvitation(String username) {
//        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
//                + username + "')]//following-sibling::td//button")).click();
//        return this;
//    }
//
//    public FriendsPage shouldHasActionAddFriend(String username) {
//        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
//                + username + "')]//following-sibling::td//button")).click();
//        return this;
//    }
//
//    public FriendsPage shouldHasActionSubmitAndDecline(String username) {
//        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
//                + username + "')]//following-sibling::td//button")).click();
//        return this;
//    }

    public FriendsPage listFriendIsNotEmpty() {
        $$(".table tbody tr").shouldHave(sizeGreaterThan(0));
        return this;
    }

}
