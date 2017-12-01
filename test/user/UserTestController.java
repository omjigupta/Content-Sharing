package user;

import org.junit.Test;
import session.SessionService;

import static org.assertj.core.api.Assertions.assertThat;

public class Login {


    private UserService userService;

    private SessionService sessionService;

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void createUser () {
        UserController userController = new UserController();

    }
}
