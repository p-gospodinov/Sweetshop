package bg.codeacademy.cakeShop.error_handling.exception;

public class NoContractException extends RuntimeException {
    public NoContractException(String message) {
        super(message);
    }
}
