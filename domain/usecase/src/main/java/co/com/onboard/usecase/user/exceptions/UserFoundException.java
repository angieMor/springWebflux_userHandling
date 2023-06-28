package co.com.onboard.usecase.user.exceptions;

public class UserFoundException extends RuntimeException{

    public UserFoundException(String message){
        super(message);
    }
}
