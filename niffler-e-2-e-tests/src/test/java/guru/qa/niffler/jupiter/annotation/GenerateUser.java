package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.jupiter.extension.GenerateUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(GenerateUserExtension.class)
public @interface GenerateUser {
    String username();
    String password() default "12345";
    String submitPassword() default "12345";
}
