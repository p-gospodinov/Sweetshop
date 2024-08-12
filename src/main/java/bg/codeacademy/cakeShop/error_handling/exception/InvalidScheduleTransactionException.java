package bg.codeacademy.cakeShop.error_handling.exception;

public class InvalidScheduleTransactionException extends RuntimeException {
    public InvalidScheduleTransactionException(String message) {
        super(message);
    }
}
