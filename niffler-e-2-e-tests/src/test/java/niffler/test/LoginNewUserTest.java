package niffler.test;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.db.dao.NifflerUsersDAO;
import niffler.db.dao.NifflerUsersDAOJdbc;
import niffler.db.entity.Authority;
import niffler.db.entity.AuthorityEntity;
import niffler.db.entity.UserEntity;
import niffler.test.web.BaseWebTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class LoginNewUserTest extends BaseWebTest {


    private NifflerUsersDAO usersDAO = new NifflerUsersDAOJdbc();
    UserEntity userEntity;

    @BeforeEach
    void createUserForTest() {
        userEntity = new UserEntity();
        userEntity.setUsername("viktor");
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
        usersDAO.createUser(userEntity);
    }

    @AllureId("104")
    @Test
    void loginTest() {
        Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
        loginPage.login(userEntity.getUsername(), userEntity.getPassword());
        headerNavigation
                .openFriends()
                .checkTextInHeader("Niffler. The coin keeper.");
    }

}
