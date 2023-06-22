package guru.qa.niffler.jupiter.extension;

import org.junit.jupiter.api.extension.*;

public class CallBackExtension implements BeforeAllCallback,
        AfterAllCallback,
        BeforeEachCallback,
        AfterEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        TestExecutionExceptionHandler {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("#### @BeforeAllCallback");
    }


    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("#### @AfterAllCallback");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println("     #### @BeforeEachCallback");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        System.out.println("     #### @AfterEachCallback");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        System.out.println("                #### @AfterTestExecutionCallback");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        System.out.println("                #### @BeforeTestExecutionCallback");
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        System.out.println("                #### @TestExecutionExceptionHandler");
        throw throwable;
    }
}
