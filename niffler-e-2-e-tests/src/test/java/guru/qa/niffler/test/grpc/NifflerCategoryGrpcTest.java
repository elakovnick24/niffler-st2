package guru.qa.niffler.test.grpc;

import guru.qa.grpc.niffler.grpc.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NifflerCategoryGrpcTest extends BaseGrpcTest {

    @Test
    void getAllCategoriesTest() {
        CategoriesResponse allCategories = categoryStub.getAllCategories(EMPTY);
        assertThat(allCategories.getCategoriesList().size()).isEqualTo(2);
    }


}
