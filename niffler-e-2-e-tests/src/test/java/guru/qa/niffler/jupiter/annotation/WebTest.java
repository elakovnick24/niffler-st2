package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.jupiter.extension.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({
        BrowserExtension.class,
        GenerateSpendExtension.class,
        GenerateCategoryExtension.class,
        ApiLoginExtension.class})
//EnvironmentExecutionCondition.class})
public @interface WebTest {

}


//TODO: Включить после доработки EnvironmentExecutionCondition
//    WebTestExecutionEnvironment enabledFor();
//
//    public enum WebTestExecutionEnvironment {
//        DEV,
//        QA,
//        PRODUCTION
//    }

