package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import niffler.jupiter.annotation.ClassPathUser;
import niffler.jupiter.annotation.GenerateCategory;
import niffler.model.CategoryJson;
import niffler.model.UserJson;
import niffler.page.LoginPage;
import niffler.page.ProfilePage;
import niffler.page.component.HeaderNavigationComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryWebTest extends BaseWebTest {

    LoginPage loginPage = new LoginPage();
    ProfilePage profilePage = new ProfilePage();
    HeaderNavigationComponent headerNavigation = new HeaderNavigationComponent();
    @BeforeEach
    void openMainPage() {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
    }

    @GenerateCategory(
            username = "Nick",
            category = "Relocate"
    )
    //TODO: Доработать parameterResolver
    @Test
    void categoryShouldBeAdded(@ClassPathUser UserJson user, CategoryJson username, CategoryJson category) {
        loginPage.login(user);
        headerNavigation.openProfile();
        profilePage.categoryExist(category, "Relocate");
    }

}
