package co.com.onboard.usecase.user;

import co.com.onboard.model.user.User;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataTestUserUseCase {

    public static User obtainUser() {
        return  new User(
                1,
                "John",
                "Doe",
                "jhon@gmail.com"
        );
    }

    public static Flux<User> obtainAllUsers() {
        User user1 = new User(
                1,
                "John",
                "Doe",
                "jhon@gmail.com"
        );

        User user2 = new User(
                2,
                "Angie",
                "Moreno",
                "angie@gmail.com"
        );
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));
        return Flux.fromIterable(users);
    }

    public static Flux<User> obtainUsersByName() {
        User user1 = new User(
                1,
                "Angie",
                "Doe",
                "ang@gmail.com"
        );

        User user2 = new User(
                2,
                "Angie",
                "Moreno",
                "angie@gmail.com"
        );
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));
        return Flux.fromIterable(users);
    }
}
