package bg.codeacademy.cakeShop.error_handling.exception;

public class OfferExistException extends RuntimeException {
    public OfferExistException(String s) {
        super(s);
    }
}
