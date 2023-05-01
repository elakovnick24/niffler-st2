package niffler.test;

import niffler.jupiter.extension.ExampleSuiteExtension;
import niffler.jupiter.extension.GenerateCategoryExtension;
import niffler.jupiter.extension.GenerateSpendExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ExampleSuiteExtension.class,
        GenerateSpendExtension.class,
        GenerateCategoryExtension.class
})
public abstract class BaseTest {

}
