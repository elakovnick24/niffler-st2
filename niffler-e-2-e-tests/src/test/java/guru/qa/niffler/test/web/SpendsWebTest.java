package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import io.qameta.allure.Allure;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpendsWebTest extends BaseWebTest {
    @BeforeEach
    void doLogin() {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
        loginPage.login("nick", "12345");
    }

    @AllureId("777")
    @GenerateCategory(
            username = "nick",
            category = "Relocate"
    )
    @GenerateSpend(
            username = "nick",
            description = "QA GURU ADVANCED VOL 2",
            currency = CurrencyValues.RUB,
            amount = 52000.00,
            category = "Relocate"
    )
    @Test
    void spendShouldBeDeletedByActionInTable(SpendJson username, SpendJson spend) {
        mainPage
                .findFirstRowInSpendTableAndSelect(spend)
                .tapOnDeleteSelected()
                .spendingTableIsEmpty();
    }
}
