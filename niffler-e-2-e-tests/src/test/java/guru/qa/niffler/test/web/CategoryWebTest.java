package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class CategoryWebTest extends BaseWebTest {

    @BeforeEach
    void openMainPage() {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
        loginPage.login("nick", "12345");
    }

    @GenerateCategory(
            username = "nick",
            category = "Relocate"
    )
    @AllureId("104")
    @Test
    void categoryShouldBeAdded(CategoryJson username, CategoryJson category) {
        header.openProfile();
        profilePage.categoryExist(category, "Relocate");
    }

}
