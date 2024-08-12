package bg.codeacademy.cakeShop.error_handling.exception;

public class UserNameExistException extends RuntimeException {
    public UserNameExistException(String message) {
        super(message);
    }
}
