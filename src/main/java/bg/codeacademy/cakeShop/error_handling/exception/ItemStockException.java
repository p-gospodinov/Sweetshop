package bg.codeacademy.cakeShop.error_handling.exception;

public class ItemStockException extends RuntimeException {
    public ItemStockException(String message) {
        super(message);
    }
}
