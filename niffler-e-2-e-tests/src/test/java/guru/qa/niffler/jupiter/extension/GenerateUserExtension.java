package guru.qa.niffler.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.niffler.api.AuthClient;
import guru.qa.niffler.api.UserRestClient;
import guru.qa.niffler.db.dao.NifflerUsersDAO;
import guru.qa.niffler.db.dao.NifflerUsersDAOSpringJdbc;
import guru.qa.niffler.db.entity.UserEntity;
import guru.qa.niffler.jupiter.annotation.GenerateUser;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.*;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class GenerateUserExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {
    public static Namespace NAMESPACE = Namespace.create(GenerateUserExtension.class);
    private final AuthClient authClient = new AuthClient();
    private final UserRestClient userRestClient = new UserRestClient();
    private Faker faker = new Faker();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        UserJson userJson = null;
        String name = faker.name().name();
        GenerateUser generateUser = context.getRequiredTestMethod().getAnnotation(GenerateUser.class);
        if (generateUser.username() != null && generateUser.username().length() > 3) {
            userJson = doRegister(generateUser.username(), generateUser.password(), generateUser.submitPassword());
        }
        if (generateUser.username().length() == 0) {
            userJson = doRegister(name,"12345","12345");
        }

        context.getStore(NAMESPACE).put(getTestId(context), userJson);
    }
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return null;
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        UserJson userJson = context.getStore(NAMESPACE).get(getTestId(context), UserJson.class);
        if (userJson != null) {
            NifflerUsersDAO nifflerUsersDAO = new NifflerUsersDAOSpringJdbc();
            UserEntity user = nifflerUsersDAO.getUser(userJson.getUsername());
            nifflerUsersDAO.removeUser(user);
        }
    }

    private UserJson doRegister(String username, String password, String submitPassword) {
        authClient.registerGetToken();
        authClient.register(username, password, submitPassword);
        return userRestClient.getCurrentUser(username);
    }

    private String getTestId(ExtensionContext context) {
       return context.getRequiredTestMethod() + " " + context.getRequiredTestClass();
    }
}
