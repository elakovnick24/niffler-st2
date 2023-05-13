package niffler.jupiter.extension;

import io.qameta.allure.AllureId;
import niffler.jupiter.annotation.User;
import niffler.jupiter.annotation.User.UserType;
import niffler.model.UserJson;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static niffler.jupiter.annotation.User.UserType.*;

public class UsersQueueExtension implements
        BeforeEachCallback,
        AfterTestExecutionCallback,
        ParameterResolver {

    // Определяется константа USER_EXTENSION_NAMESPACE, которая представляет Namespace для хранилища расширения.
    public static Namespace USER_EXTENSION_NAMESPACE = Namespace
            .create(UsersQueueExtension.class);

    // Создается и инициализируется Map USERS_QUEUES, содержащая Queue объектов типа UserJson для разных значений UserType.
    private final static Map<UserType, Queue<UserJson>> USERS_QUEUES = new ConcurrentHashMap<>();

    static {
        USERS_QUEUES.put(WITH_FRIENDS, new LinkedList<>(List.of(
                userJson("nick", "12345"),
                userJson("kyle", "12345"))
        ));

        USERS_QUEUES.put(INVITATION_SENT, new LinkedList<>(
                List.of(userJson("emma", "12345"),
                        userJson("emily", "12345"))
        ));

        USERS_QUEUES.put(INVITATION_RECEIVED, new LinkedList<>(
                List.of(userJson("anna", "12345"),
                        userJson("bill", "12345"))
        ));
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        // Получение testId с помощью метода getTestId
        final String testId = getTestId(context);
        // Инициализация Map users
        Map<UserType, Map<String, UserJson>> users = new HashMap();
        // Фильтрует и получает параметры метода теста getParameters(), помеченные аннотацией @User.
        List<Parameter> testParameters = Arrays.stream(
                        context.getRequiredTestMethod()
                                .getParameters())
                //Параметр содержит аннотацию User? Если, да оставь в стриме
                .filter(p -> p.isAnnotationPresent(User.class))
                .toList();
        // В цикле проихводится проход по каждому параметру из списка testParameters
        for (Parameter parameter : testParameters) {
            // Проверка на null, не требуется тк выше отфильтровали в стриме
            // Получение UserType из аннотации User.class
            UserType userType = parameter.getAnnotation(User.class).userType();
            UserJson user = null;
            while (user == null) {
                // Извлечение объекта UserJson из соответствующей очереди в USERS_QUEUES
                user = USERS_QUEUES.get(userType).poll();
            }
            // Сохранение извлеченного объекта UserJson в Map users с использованием getName() в качестве key.
            users.putIfAbsent(userType, new HashMap<>());
            users.get(userType)
                    .put(parameter.getName(), user);
        }
        // Сохранение Map users в store, с использованием testId в качестве key.
        context.getStore(USER_EXTENSION_NAMESPACE)
                .put(testId, users);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        // Получение testId с помощью метода getTestId
        final String testId = getTestId(context);
        // Извлечение Map users из store, используя testId
        Map<UserType, Map<String, UserJson>> users =
                context.getStore(USER_EXTENSION_NAMESPACE)
                        .get(testId, Map.class);
        // Добавляет все объекты UserJson из Map users обратно в соответствующую очередь в USERS_QUEUES
        for (Map.Entry<UserType, Map<String, UserJson>> entryUsers : users.entrySet()) {
            USERS_QUEUES.get(entryUsers.getKey()).addAll(
                    entryUsers.getValue().values());
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext context) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(User.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    // Метод resolveParameter разрешает значение для параметра, помеченного аннотацией @User
    @SuppressWarnings("unchecked")
    @Override
    public UserJson resolveParameter(ParameterContext parameterContext,
                                     ExtensionContext context) throws ParameterResolutionException {
        // Извлечение testId, parameter name
        final String testId = getTestId(context);
        String name = parameterContext.getParameter().getName();
        // Извлечение Map users из хранилища расширения по testId
        Map<UserType, Map<String, UserJson>> users = context
                .getStore(USER_EXTENSION_NAMESPACE)
                .get(testId, Map.class);
        return users
                .get(parameterContext
                        .getParameter()
                        .getAnnotation(User.class)
                        .userType())
                .get(name);
    }

    // Метод getTestId извлекает идентификатор теста из аннотации AllureId на методе тестирования
    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }

    private static UserJson userJson(String userName, String password) {
        UserJson user = new UserJson();
        user.setUsername(userName);
        user.setPassword(password);
        return user;
    }
}
