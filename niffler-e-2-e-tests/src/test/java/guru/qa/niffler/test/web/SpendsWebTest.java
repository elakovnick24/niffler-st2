package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.GenerateUser;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;

public class SpendsWebTest extends BaseWebTest {

    @GenerateSpend(
            username = "nick",
            description = "QA GURU ADVANCED VOL 2",
            currency = CurrencyValues.RUB,
            amount = 52000.00,
            category = "Relocate"
    )
    @GenerateUser(username = "asdad")
    @AllureId("777")
    @Test
    void spendShouldBeDeletedByActionInTable(SpendJson spend) {
        Selenide.open(CFG.getFrontUrl() + "/main");
        mainPage
                .findFirstRowInSpendTableAndSelect(spend)
                .tapOnDeleteSelected()
                .spendingTableIsEmpty();
    }

    @GenerateUser(username = "Name"
    )
    @Test
    void generateUserApi(UserJson userJson) {
        System.out.println(userJson);
    }
}
