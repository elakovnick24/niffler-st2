package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import niffler.jupiter.annotation.GenerateCategory;
import niffler.model.CategoryJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    @Test
    void categoryShouldBeAdded(CategoryJson username, CategoryJson category) {
        headerNavigation.openProfile();
        profilePage.categoryExist(category, "Relocate");
    }

}
