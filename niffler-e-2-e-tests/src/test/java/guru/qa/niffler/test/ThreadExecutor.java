package guru.qa.niffler.test;

import guru.qa.niffler.test.web.RegistrationWebTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        executorService.execute(() -> {
            RegistrationWebTest testObject = new RegistrationWebTest();
            testObject.errorMessageShouldBeVisibleInCaseThatPasswordAreDifferent();
        });
        executorService.execute(() -> {
            RegistrationWebTest testObject = new RegistrationWebTest();
            testObject.errorMessageShouldBeVisibleInCaseThatUsernameLessThan3Symbols();
        });

        executorService.execute(() -> {
            RegistrationWebTest testObject = new RegistrationWebTest();
            testObject.errorMessageShouldBeVisibleInCaseThatPasswordLessThan3Symbols();
        });

        executorService.execute(() -> {
            RegistrationWebTest testObject = new RegistrationWebTest();
            testObject.errorMessageShouldBeVisibleInCaseThatUsernameNotUniq();
        });
        executorService.shutdown();
    }
}
