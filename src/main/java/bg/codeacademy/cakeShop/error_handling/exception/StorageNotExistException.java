package bg.codeacademy.cakeShop.error_handling.exception;

public class StorageNotExistException extends RuntimeException {
    public StorageNotExistException(String message) {
        super(message);
    }
}
