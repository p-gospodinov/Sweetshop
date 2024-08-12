package bg.codeacademy.cakeShop.error_handling.exception;

public class ScheduleTransactionExistException extends RuntimeException {
    public ScheduleTransactionExistException(String message) {
        super(message);
    }
}
