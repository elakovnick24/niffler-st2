package niffler.test.web;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import niffler.jupiter.annotation.ClassPathUser;
import niffler.jupiter.annotation.GenerateCategory;
import niffler.jupiter.annotation.GenerateSpend;
import niffler.model.CurrencyValues;
import niffler.model.SpendJson;
import niffler.model.UserJson;
import niffler.test.page.LoginPage;
import niffler.test.page.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;

@Disabled
public class SpendsWebTest extends BaseWebTest {

    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();
    @BeforeEach
    void doLogin() {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
    }

    @ValueSource(strings = {
            "testdata/dima.json"
    })
    @GenerateCategory(
            username = "Nick",
            category = "Relocate"
    )
    @GenerateSpend(
        username = "Nick",
        description = "QA GURU ADVANCED VOL 2",
        currency = CurrencyValues.RUB,
        amount = 52000.00,
        category = "Relocate"
    )
    //TODO: Доработать parameterResolver
    @Test
    void spendShouldBeDeletedByActionInTable(@ClassPathUser UserJson user, SpendJson spend) {
        loginPage
                .login(user);
        mainPage
                .findFirstRowInSpendTableAndSelect(spend)
                .tapOnDeleteSelected()
                .spendingTableIsEmpty();
    }
}
