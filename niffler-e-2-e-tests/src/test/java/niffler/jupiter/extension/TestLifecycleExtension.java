package niffler.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestLifecycleExtension implements BeforeEachCallback,
        BeforeTestExecutionCallback,
        BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.print("-----------> Before All Context ----------->  ");
        try {
            context.getRequiredTestMethod();
            System.out.print("----------->  getRequiredTestMethod() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestClass();
            System.out.print("----------->  getRequiredTestClass() ----------->  ");
        } catch (Exception e){};
        try {
            context.getRequiredTestInstance();
            System.out.print("----------->  getRequiredTestInstance() -----------> ");
        } catch (Exception e){}
        System.out.println();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.print("-----------> Before Each -----------> ");
        try {
            context.getRequiredTestMethod();
            System.out.print("----------->  getRequiredTestMethod() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestClass();
            System.out.print("----------->  getRequiredTestClass() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestInstance();
            System.out.print("----------->  getRequiredTestInstance() -----------> ");
        } catch (Exception e){}
        System.out.println();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        System.out.print("----------->  Before Test Execution -----------> ");
        try {
            context.getRequiredTestMethod();
            System.out.print("-----------> getRequiredTestMethod() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestClass();
            System.out.print("-----------> getRequiredTestClass() -----------> ");
        } catch (Exception e){};
        try {
            context.getRequiredTestInstance();
            System.out.print("-----------> getRequiredTestInstance() -----------> ");
        } catch (Exception e){}
        System.out.println();
    }
}
