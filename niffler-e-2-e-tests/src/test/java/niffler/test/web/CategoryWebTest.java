package niffler.test.web;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import niffler.jupiter.annotation.GenerateCategory;
import niffler.model.CategoryJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoryWebTest extends BaseWebTest {

    @BeforeEach
    void doLogin() {


        Selenide.open("/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue("Nick");
        $("input[name='password']").setValue("123qweasd");
        $("button[type='submit']").click();
    }

    @GenerateCategory(
            username = "Nick",
            category = "Relocate"

    )
    @Test
    void categoryShouldBeAdded(CategoryJson username, CategoryJson category) {
        open("/profile");
        ElementsCollection categories = $$(".main-content__section-categories ul");

        assertAll(
                () -> categories.forEach(c -> c.getAttribute(category.getCategory()).equals("Relocate"))
        );
    }

}
