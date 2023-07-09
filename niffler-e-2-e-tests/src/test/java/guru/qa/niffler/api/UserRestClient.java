package guru.qa.niffler.api;

import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.Assertions;
import org.springframework.lang.NonNull;
import java.io.IOException;

public class UserRestClient extends BaseRestClient{
    public UserRestClient() {
        super(
                CFG.getUserUrl()
        );
    }

    private final UserService userService = retrofit.create(UserService.class);

    public @NonNull UserJson getCurrentUser(String name) {
        try {
            return userService.getCurrentUserOrCreateIfAbsent(name).execute().body();
        } catch (IOException e) {
            Assertions.fail("Unsuccessful connect to server niffler-userdata" + e.getMessage());
            return null;
        }
    }
}
