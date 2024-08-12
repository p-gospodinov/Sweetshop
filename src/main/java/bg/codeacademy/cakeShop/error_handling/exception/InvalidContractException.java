package bg.codeacademy.cakeShop.error_handling.exception;

public class InvalidContractException extends RuntimeException {
    public InvalidContractException(String message) {
        super(message);
    }
}
