package niffler.test.api;

import io.qameta.allure.AllureId;
import niffler.api.UserService;
import niffler.jupiter.annotation.ClassPathUser;
import niffler.model.UserJson;
import niffler.test.web.BaseWebTest;
import okhttp3.OkHttpClient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class UpdateUserDataTest extends BaseWebTest {

    @ValueSource(strings = {
            "testdata/nick.json",
            "testdata/anna.json"
    })
    @AllureId("105")
    @ParameterizedTest
    void updateUserDataTest(@ClassPathUser UserJson user) throws IOException {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://127.0.0.1:8093")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        retrofit.create(UserService.class)
                .updateUserData(user)
                .execute()
                .body();

    }
}
