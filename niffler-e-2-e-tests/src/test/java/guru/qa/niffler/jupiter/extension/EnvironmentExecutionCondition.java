/*
package niffler.jupiter.extension;

import niffler.jupiter.annotation.WebTest;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;

// TODO: Переделать. Не получается засетать значение для environment
public class EnvironmentExecutionCondition implements ExecutionCondition {
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        String activeEnvironment = System.getProperty("environment");

        setEnabledEnvironments(context);

        if (activeEnvironment == null) {
            System.out.println(disabled("there's no active environment"));
        }

        String enabledEnvironments = getEnabledEnvironments(context);
        return enabledEnvironments.equals(activeEnvironment)
                ? enabled("active environment is enabled")
                : disabled("active environment isn't enabled");
    }

    // TODO: Переделать. Не получается засетать значение для environment
    private void setEnabledEnvironments(ExtensionContext context) {
        String activeEnvironment = System.getProperty("environment");
        AtomicReference<String> enabledEnvironments = new AtomicReference<>("");

        if (activeEnvironment == null) {
            context.getElement().ifPresent(element -> AnnotationSupport.findAnnotation(element, WebTest.class)
                    .map(WebTest::enabledFor)
                    .ifPresent(str -> enabledEnvironments.set(String.valueOf(str)))
            );
        }
        System.setProperty("environment", String.valueOf(enabledEnvironments));

    }

    private String getEnabledEnvironments(ExtensionContext context) {
        AtomicReference<String> enabledEnvironments = new AtomicReference<>("");
        context.getElement().ifPresent(element -> AnnotationSupport.findAnnotation(element, WebTest.class)
                .map(WebTest::enabledFor)
                .ifPresent(str -> enabledEnvironments.set(String.valueOf(str)))
        );
        return String.valueOf(enabledEnvironments);
    }

}
*/
