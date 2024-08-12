package bg.codeacademy.cakeShop.error_handling.exception;

public class LegalEntityNotFoundException extends RuntimeException {
    public LegalEntityNotFoundException(String s) {
        super(s);
    }
}
