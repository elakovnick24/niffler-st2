package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.jupiter.extension.ClassPathUserConverter;
import org.junit.jupiter.params.converter.ConvertWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ConvertWith(ClassPathUserConverter.class)
public @interface ClassPathUser {

}
