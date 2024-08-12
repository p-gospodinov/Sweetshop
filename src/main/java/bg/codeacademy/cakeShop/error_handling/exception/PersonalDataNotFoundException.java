package bg.codeacademy.cakeShop.error_handling.exception;

public class PersonalDataNotFoundException extends RuntimeException {
    public PersonalDataNotFoundException(String message) {
        super(message);
    }
}
