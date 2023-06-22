package guru.qa.niffler.test.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import guru.qa.niffler.model.SpendJson;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage extends BasePage{

    ElementsCollection bodyTable = $(".spendings-table tbody").$$("tr");
    ElementsCollection deleteButton = $$(".button_type_small");

    @Override
    public BasePage checkThatPageLoaded() {
        return this;
    }

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
        return this;
    }

}






