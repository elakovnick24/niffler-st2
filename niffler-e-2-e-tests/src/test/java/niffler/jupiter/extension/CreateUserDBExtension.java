package niffler.jupiter.extension;

import com.github.javafaker.Faker;
import niffler.db.dao.NifflerUsersDAO;
import niffler.db.dao.NifflerUsersDAOHibernate;
import niffler.db.dao.NifflerUsersDAOJdbc;
import niffler.db.dao.NifflerUsersDAOSpringJdbc;
import niffler.db.entity.Authority;
import niffler.db.entity.AuthorityEntity;
import niffler.db.entity.UserEntity;
import niffler.jupiter.annotation.GenerateUserWith;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Parameter;
import java.sql.SQLException;
import java.util.*;

public class CreateUserDBExtension implements
        BeforeEachCallback,
        ParameterResolver,
        AfterEachCallback {

    public static ExtensionContext.Namespace USER_DB_NAMESPACE = ExtensionContext.Namespace
            .create(CreateUserDBExtension.class);
    private Faker faker = new Faker();
    private NifflerUsersDAO usersDAO;

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        final String testId = context.getRequiredTestClass() + context.getRequiredTestMethod().toString();
        List<UserEntity> userEntity = new ArrayList<>();
        List<Parameter> parameters = Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(GenerateUserWith.class))
                .filter(parameter -> parameter.getType().isAssignableFrom(UserEntity.class))
                .toList();

        for (Parameter parameter : parameters) {
            GenerateUserWith annotation = parameter.getAnnotation(GenerateUserWith.class);
            if (GenerateUserWith.ClientDB.JDBC.equals(annotation.clientDB())) {
                usersDAO = new NifflerUsersDAOJdbc();
            }
            if (GenerateUserWith.ClientDB.SPRING_JDBC.equals(annotation.clientDB())) {
                usersDAO = new NifflerUsersDAOSpringJdbc();
            }
            if (GenerateUserWith.ClientDB.HIBERNATE.equals(annotation.clientDB())) {
                usersDAO = new NifflerUsersDAOHibernate();
            }
            UserEntity entity = new UserEntity();
            entity.setUsername("".equals(annotation.username()) ? faker.funnyName().name() : annotation.username());
            entity.setPassword("12345");
            entity.setEnabled(annotation.enabled());
            entity.setAccountNonExpired(annotation.accountNonExpired());
            entity.setAccountNonLocked(annotation.accountNonLocked());
            entity.setCredentialsNonExpired(annotation.credentialsNonExpired());
            entity.setAuthorities(Arrays.stream(Authority.values()).map(
                    a -> {
                        AuthorityEntity authorityEntity = new AuthorityEntity();
                        authorityEntity.setAuthority(a);
                        authorityEntity.setUser(entity);
                        return authorityEntity;
                    }
            ).toList());
            userEntity.add(entity);
            usersDAO.createUser(entity);
        }
        context.getStore(USER_DB_NAMESPACE).put(testId, userEntity);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserEntity resolveParameter(ParameterContext parameterContext,
                                       ExtensionContext extensionContext) throws ParameterResolutionException {
        final String testId = extensionContext.getRequiredTestClass() + extensionContext.getRequiredTestMethod().toString();
        List<UserEntity> userEntityList = extensionContext.getStore(USER_DB_NAMESPACE).get(testId, List.class);
        return userEntityList.get(parameterContext.getIndex());
    }

    @Override
    public void afterEach(ExtensionContext context) throws SQLException {
        final String testId = context.getRequiredTestClass() + context.getRequiredTestMethod().toString();
        List<UserEntity> userEntityList = context.getStore(USER_DB_NAMESPACE).get(testId, List.class);
        List<Parameter> parameters = Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(GenerateUserWith.class))
                .toList();

        for (Parameter parameter : parameters) {
            GenerateUserWith annotation = parameter.getAnnotation(GenerateUserWith.class);
            // Удалять ли юзера после каждого теста?
            if (annotation.deleteUserAfterEach()) {
                for (UserEntity userEntity : userEntityList) {
                    usersDAO.removeUser(userEntity);
                }
            }
        }
    }
}
