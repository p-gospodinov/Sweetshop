package bg.codeacademy.cakeShop.error_handling.exception;

public class UniqueIdentificationNumberExistException extends RuntimeException {
    public UniqueIdentificationNumberExistException(String message) {
        super(message);
    }
}
