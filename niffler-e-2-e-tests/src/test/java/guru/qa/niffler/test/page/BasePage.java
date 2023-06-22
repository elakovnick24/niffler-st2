package guru.qa.niffler.test.page;

public abstract class BasePage<T extends BasePage> {

    public abstract T checkThatPageLoaded();
}
