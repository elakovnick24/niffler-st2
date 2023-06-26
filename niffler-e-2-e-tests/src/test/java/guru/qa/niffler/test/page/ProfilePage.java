package guru.qa.niffler.test.page;

import com.codeborne.selenide.ElementsCollection;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.test.page.component.Header;

import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProfilePage extends BasePage{
    ElementsCollection categories = $$(".main-content__section-categories ul");

    Header header = new Header();

    @Override
    public ProfilePage checkThatPageLoaded() {
        header.checkThatComponentDisplayer();
        return this;
    }

    public ProfilePage categoryExist(CategoryJson category, String expectedCategory) {
        assertAll(
                () -> categories.forEach(c -> c.getAttribute(category.getCategory())
                        .equals(expectedCategory))
        );
        return this;
    }

}
