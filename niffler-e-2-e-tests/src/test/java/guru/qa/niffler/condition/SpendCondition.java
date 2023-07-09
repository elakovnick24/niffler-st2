package guru.qa.niffler.condition;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.impl.CollectionSource;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static guru.qa.niffler.helpers.DateFormatConverter.convertWebElementFormatToSpendJsonFormatDate;

public class SpendCondition {

    public static CollectionCondition spends(SpendJson... expectedSpends) {
        return new CollectionCondition() {
            @Override
            public void fail(CollectionSource collection, @Nullable List<WebElement> elements, @Nullable Exception lastError, long timeoutMs) {
                if (elements == null || elements.isEmpty()) {
                    throw new ElementNotFound(collection, toString(), timeoutMs, lastError);
                } else if (elements.size() != expectedSpends.length) {
                    throw new SpendsSizeMismatch(collection,
                            Arrays.asList(expectedSpends), elements.stream().map(
                            SpendCondition::convertWebElementToSpendJson).toList(),
                            explanation,
                            timeoutMs);
                } else {
                    throw new SpendsMismatch(collection,
                            Arrays.asList(expectedSpends), elements.stream().map(
                            SpendCondition::convertWebElementToSpendJson).toList(),
                            explanation,
                            timeoutMs);
                }
            }

            @Override
            public boolean missingElementSatisfiesCondition() {
                return false;
            }

            @Override
            public boolean test(List<WebElement> webElements) {
                if (webElements.size() != expectedSpends.length) {
                    return false;
                }

                for (int i = 0; i < expectedSpends.length; i++) {
                    WebElement row = webElements.get(i);
                    SpendJson expectedSpend = expectedSpends[i];
                    if (!spendRowEqualsSpend(convertWebElementToSpendJson(row), expectedSpend)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    private static boolean spendRowEqualsSpend(SpendJson actualSpend, SpendJson expectedSpend) {
        return
                actualSpend.getSpendDate().toString().equals(expectedSpend.getSpendDate().toString())
                && actualSpend.getAmount().toString().equals(expectedSpend.getAmount().toString())
                && actualSpend.getCurrency().toString().equals(expectedSpend.getCurrency().toString())
                && actualSpend.getCategory().toString().equals(expectedSpend.getCategory().toString())
                && actualSpend.getDescription().toString().equals(expectedSpend.getDescription().toString());

    }

    private static SpendJson convertWebElementToSpendJson(WebElement row) {
        SpendJson sj = new SpendJson();
        String webElementDate = row.findElements(By.cssSelector("td")).get(1).getText();
        String amount = row.findElements(By.cssSelector("td")).get(2).getText();
        CurrencyValues currency = CurrencyValues.valueOf(row.findElements(By.cssSelector("td")).get(3).getText());
        String category = row.findElements(By.cssSelector("td")).get(4).getText();
        String description = row.findElements(By.cssSelector("td")).get(5).getText();

        sj.setSpendDate(convertWebElementFormatToSpendJsonFormatDate(webElementDate));
        sj.setAmount(Double.valueOf(amount));
        sj.setCurrency(currency);
        sj.setCategory(category);
        sj.setDescription(description);
        return sj;
    }

}
