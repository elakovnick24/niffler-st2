package niffler.page;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class PeoplePage {

    ElementsCollection invitationTable = $$(".table tbody tr");

    public PeoplePage findInvitationPending() {
        invitationTable.find(text("Pending invitation"))
                .should(visible);
        return this;
    }

}
