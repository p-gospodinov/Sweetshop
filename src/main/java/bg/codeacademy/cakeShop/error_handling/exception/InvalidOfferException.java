package bg.codeacademy.cakeShop.error_handling.exception;

public class InvalidOfferException extends RuntimeException {
    public InvalidOfferException(String message) {
        super(message);
    }
}
