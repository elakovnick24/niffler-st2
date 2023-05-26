package niffler.jupiter.extension;

import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import niffler.db.dao.NifflerUsersDAO;
import niffler.db.dao.NifflerUsersDAOJdbc;
import niffler.db.entity.Authority;
import niffler.db.entity.AuthorityEntity;
import niffler.db.entity.UserEntity;
import org.junit.jupiter.api.extension.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class CreateUserDBExtension implements
        BeforeEachCallback,
        ParameterResolver,
        AfterTestExecutionCallback {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
            .create(UsersQueueExtension.class);
    private final NifflerUsersDAO usersDAO = new NifflerUsersDAOJdbc();
    private static final UserEntity userEntity;

    static {
        Faker faker = new Faker();
        userEntity = new UserEntity();
        userEntity.setUsername(faker.funnyName().name());
        userEntity.setPassword("12345");
        userEntity.setEnabled(true);
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setAuthorities(Arrays.stream(Authority.values()).map(
                a -> {
                    AuthorityEntity authorityEntity = new AuthorityEntity();
                    authorityEntity.setAuthority(a);
                    return authorityEntity;
                }
        ).toList());
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        final String testId = getTestId(context);
        usersDAO.createUser(userEntity);

        context.getStore(NAMESPACE)
                .put(testId, userEntity);

    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserEntity.class);
    }

    @Override
    public UserEntity resolveParameter(ParameterContext parameterContext,
                                       ExtensionContext extensionContext) throws ParameterResolutionException {
        final String testId = getTestId(extensionContext);
        return extensionContext.getStore(NAMESPACE).get(testId, UserEntity.class);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws SQLException {
        final String testId = getTestId(context);
        UserEntity userEntity = context.getStore(NAMESPACE)
                .get(testId, UserEntity.class);
        usersDAO.deleteUser(userEntity);
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }
}
