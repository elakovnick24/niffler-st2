package guru.qa.niffler.test.page;

import com.codeborne.selenide.ElementsCollection;
import guru.qa.niffler.test.page.component.Header;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PeoplePage extends BasePage{

    ElementsCollection invitationTable = $$(".table tbody tr");

    Header header = new Header();

    @Override
    public PeoplePage checkThatPageLoaded() {
        header.checkThatComponentDisplayer();
        return this;
    }
    public PeoplePage findInvitationPending() {
        invitationTable.find(text("Pending invitation"))
                .should(visible);
        return this;
    }


}
