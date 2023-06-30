package co.com.onboard.api;

import co.com.onboard.model.user.User;

public class DataTestHandler {

    public static User obtainUser() {
        return  new User(
                1,
                "John",
                "Doe",
                "jhon@gmail.com"
        );
    }
}
