package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.jupiter.extension.GqlResourceFileResolver;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@ExtendWith(GqlResourceFileResolver.class)
public @interface GqlRequest {
    String value();
}
