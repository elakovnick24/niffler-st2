package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
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
    @ApiLogin(username = "nick", password = "12345")
    @AllureId("777")
    @Test
    void spendShouldBeDeletedByActionInTable(SpendJson spend) {
        Selenide.open(CFG.getFrontUrl() + "/main");
        mainPage
                .findFirstRowInSpendTableAndSelect(spend)
                .tapOnDeleteSelected()
                .spendingTableIsEmpty();
    }
}
