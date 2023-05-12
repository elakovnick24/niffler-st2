package niffler.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import niffler.model.SpendJson;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    ElementsCollection bodyTable = $(".spendings-table tbody").$$("tr");
    ElementsCollection deleteButton = $$(".button_type_small");

    public MainPage findFirstRowInSpendTableAndSelect(SpendJson spend) {
        bodyTable
                .find(Condition.text(spend.getDescription()))
                .$$("td").first()
                .scrollTo()
                .click();
        return this;
    }

    public MainPage tapOnDeleteSelected() {
        deleteButton.find(Condition.text(("Delete selected"))).click();
        return this;
    }

    public MainPage spendingTableIsEmpty() {
        bodyTable
                .shouldHave(CollectionCondition.size(0));
        throw new IllegalStateException();
    }
}






