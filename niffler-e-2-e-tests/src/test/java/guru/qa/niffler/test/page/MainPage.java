package guru.qa.niffler.test.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.test.page.component.Header;
import org.hibernate.query.sqm.sql.FromClauseIndex;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage extends BasePage {

    public static final String URL = Config.getConfig().getFrontUrl() + "/main";

    Header header = new Header();

    private final SelenideElement headerOfSpendSection = $(By.linkText("Add new spending"));
    private final SelenideElement headerCategory = $(".add-spending__form .form__label:first-child");
    private final SelenideElement headerAmount = $(".add-spending__form .form__label:nth-child(2)");
    private final SelenideElement headerSpendDate = $(".add-spending__form .form__label:nth-child(3)");
    private final SelenideElement headerDescription = $(".add-spending__form .form__label:nth-child(4)");
    private final SelenideElement formError = $(".form__error");
    private final SelenideElement chooseSpendingInputField = $(".main-content__section-add-spending [class$=Input]");
    private final SelenideElement spendingCategoryDropDownList = $("[class$=MenuList]");
    private final SelenideElement amountInput = $(".form__input[type='number']");
    private final SelenideElement spendDateInput  = $("[data-form-type='date']");
    private final SelenideElement spendCalPop = $(".main-content__section.main-content__section-add-spending .react-datepicker-popper");
    private final SelenideElement descriptionInput = $(".form__input[type='text']");
    private final SelenideElement addSpendBtn = $(".button[type='submit']");
    private final ElementsCollection bodyTable = $(".spendings-table tbody").$$("tr");
    private final ElementsCollection deleteBtn = $$(".button_type_small");

    @Override
    public MainPage checkThatPageLoaded() {
        header.checkThatComponentDisplayer();
//        headerOfSpendSection.shouldHave(text("Add new spending"));
        headerCategory.shouldHave(text("Category"));
        headerCategory.shouldHave(text("*"));
        headerAmount.shouldHave(text("Amount"));
        headerAmount.shouldHave(text("*"));
        headerSpendDate.shouldHave(text("Spend date"));
        headerSpendDate.shouldHave(text("*"));
        headerDescription.shouldHave(text("Description"));
        return this;
    }

    public MainPage addNewSpend() {
        chooseSpendingInputField.click();
        spendingCategoryDropDownList.$$("[class$=option]").find(exactText("QA GURU")).click();
        amountInput.val("56000");
//        spendDateInput.clear();
//        spendDateInput.val("23.04.2023");
        descriptionInput.val("The best course!");
        return this;
    }

    public MainPage findFirstRowInSpendTableAndSelect(SpendJson spend) {
        bodyTable
                .find(text(spend.getDescription()))
                .$$("td").first()
                .scrollTo()
                .click();
        return this;
    }

    public MainPage findFirstRowInSpendTableAndSelect(String spend) {
        bodyTable
                .find(text(spend))
                .$$("td").first()
                .scrollTo()
                .click();
        return this;
    }

    public MainPage tapOnDeleteSelected() {
        deleteBtn.find(text(("Delete selected"))).click();
        return this;
    }

    public MainPage spendingTableIsEmpty() {
        bodyTable
                .shouldHave(CollectionCondition.size(0));
        return this;
    }

    public FriendsPage goToFriendPage() {
       return header.openFriends();
    }

    public PeoplePage goToPeoplePage() {
        return header.openPeople();
    }

    public ProfilePage goToProfilePage() {
        return header.openProfile();
    }

    public StartPage logOut() {
        return header.logout();
    }
}






