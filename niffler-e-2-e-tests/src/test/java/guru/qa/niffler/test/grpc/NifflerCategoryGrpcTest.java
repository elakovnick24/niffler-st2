package guru.qa.niffler.test.grpc;

import com.github.javafaker.Faker;
import guru.qa.grpc.niffler.grpc.*;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NifflerCategoryGrpcTest extends BaseGrpcTest {

    private Faker faker = new Faker();

    @Test
    void successGetAllCategoriesForUser() {
        CategoriesRequest categoriesRequest = CategoriesRequest.newBuilder().setUsername("dima").build();
        CategoriesResponse categoriesResponse = categoryStub.getAllCategories(categoriesRequest);
        List<Category> categoryList = categoriesResponse.getCategoriesList();
        assertThat(categoryList.get(0).getCategory())
                .isEqualTo("QA GURU");
        assertThat(categoryList.get(1).getCategory())
                .isEqualTo("Fishing");
    }

    @Test
    void addCategoriesForUser() {
        String name = faker.name().firstName();
        String category = faker.book().title();;
        CategoryRequest categoryRequest = CategoryRequest.newBuilder()
                .setUsername(name)
                .setCategory(CategoryReq.newBuilder()
                        .setUsername(name)
                        .setCategory(category)).build();

        CategoryResponse categoryResponse = categoryStub.addCategory(categoryRequest);
        assertThat(categoryResponse.getCategory().getCategory())
                .isEqualTo(category);
    }


}
