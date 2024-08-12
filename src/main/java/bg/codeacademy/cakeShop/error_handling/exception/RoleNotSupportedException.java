package bg.codeacademy.cakeShop.error_handling.exception;

public class RoleNotSupportedException extends RuntimeException {
    public RoleNotSupportedException(String message) {
        super(message);
    }
}
