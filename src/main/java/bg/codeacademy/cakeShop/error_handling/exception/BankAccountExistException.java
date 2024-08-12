package bg.codeacademy.cakeShop.error_handling.exception;

public class BankAccountExistException extends RuntimeException {
    public BankAccountExistException(String message) {
        super(message);
    }
}
