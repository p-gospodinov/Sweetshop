package bg.codeacademy.cakeShop.error_handling.exception;

public class BankAccountNotExistException extends RuntimeException {
    public BankAccountNotExistException(String message) {
        super(message);
    }
}
