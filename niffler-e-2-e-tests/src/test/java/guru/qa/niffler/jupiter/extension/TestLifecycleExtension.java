package guru.qa.niffler.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestLifecycleExtension implements BeforeEachCallback,
        BeforeTestExecutionCallback,
        BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        System.out.println("-----------> Before All Context ----------->  ");
        try {
            context.getRequiredTestMethod();
            System.out.println("----------->  getRequiredTestMethod() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestClass();
            System.out.println("----------->  getRequiredTestClass() ----------->  ");
        } catch (Exception e){};
        try {
            context.getRequiredTestInstance();
            System.out.println("----------->  getRequiredTestInstance() -----------> ");
        } catch (Exception e){}
        System.out.println();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println("-----------> Before Each Context -----------> ");
        try {
            context.getRequiredTestMethod();
            System.out.println("----------->  getRequiredTestMethod() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestClass();
            System.out.println("----------->  getRequiredTestClass() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestInstance();
            System.out.println("----------->  getRequiredTestInstance() -----------> ");
        } catch (Exception e){}
        System.out.println();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        System.out.println("----------->  Before Test Execution -----------> ");
        try {
            context.getRequiredTestMethod();
            System.out.println("-----------> getRequiredTestMethod() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestClass();
            System.out.println("-----------> getRequiredTestClass() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestInstance();
            System.out.println("-----------> getRequiredTestInstance() -----------> ");
        } catch (Exception e){}
        System.out.println();
    }
}
