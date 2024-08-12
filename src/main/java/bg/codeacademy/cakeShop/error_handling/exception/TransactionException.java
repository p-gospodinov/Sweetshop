package bg.codeacademy.cakeShop.error_handling.exception;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
