package co.com.onboard.r2dbc.mapper;


import co.com.onboard.model.user.User;
import co.com.onboard.r2dbc.persistence.UserPersistence;

public class UserMapper {

    public static User toUser(UserPersistence userPersistence){

        return User.builder()
                .id(userPersistence.getId())
                .firstName(userPersistence.getFirstName())
                .lastName(userPersistence.getLastName())
                .email(userPersistence.getEmail())
                .build();
    }

    public static UserPersistence toPersistenceEntity(User user){

        return UserPersistence.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

}
