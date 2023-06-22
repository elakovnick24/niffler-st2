package guru.qa.niffler.jupiter.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import static org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;

public interface SuiteExtension extends BeforeAllCallback {

    default void beforeSuite() {
    }

    default void afterSuite() {
    }

    @Override
    default void beforeAll(ExtensionContext context) throws Exception {
        context.getRoot().getStore(Namespace.GLOBAL)
                .getOrComputeIfAbsent(
                        SuiteExtension.class,
                        k -> {
                            beforeSuite();
                            return (CloseableResource) this::afterSuite;
                        });
    }
}
