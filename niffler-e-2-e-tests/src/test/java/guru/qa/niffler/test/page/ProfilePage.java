package guru.qa.niffler.test.page;

import com.codeborne.selenide.ElementsCollection;
import guru.qa.niffler.model.CategoryJson;

import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProfilePage {
    ElementsCollection categories = $$(".main-content__section-categories ul");

    public ProfilePage categoryExist(CategoryJson category, String expectedCategory) {
        assertAll(
                () -> categories.forEach(c -> c.getAttribute(category.getCategory())
                        .equals(expectedCategory))
        );
        return this;
    }
}
