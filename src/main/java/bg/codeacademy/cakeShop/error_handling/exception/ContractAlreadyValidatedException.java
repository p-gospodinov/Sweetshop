package bg.codeacademy.cakeShop.error_handling.exception;

public class ContractAlreadyValidatedException extends RuntimeException {
    public ContractAlreadyValidatedException(String message) {
        super(message);
    }
}
