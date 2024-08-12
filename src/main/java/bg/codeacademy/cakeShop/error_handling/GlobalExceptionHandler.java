package bg.codeacademy.cakeShop.error_handling;

import bg.codeacademy.cakeShop.error_handling.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({BankAccountExistException.class})
    public ResponseEntity<Object> handleBankAccountExistException(BankAccountExistException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({UniqueIdentificationNumberExistException.class})
    public ResponseEntity<Object> handleUniqueIdentificationNumberExistException(UniqueIdentificationNumberExistException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({UserNameExistException.class})
    public ResponseEntity<Object> handUserNameExistException(UserNameExistException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handUserNotFoundException(UserNotFoundException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({LegalEntityNotFoundException.class})
    public ResponseEntity<Object> handLegalEntityNotFoundException(LegalEntityNotFoundException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({RoleNotSupportedException.class})
    public ResponseEntity<Object> handleRoleNotSupportedException(RoleNotSupportedException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({OfferExistException.class})
    public ResponseEntity<Object> handleOfferExistException(OfferExistException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({InvalidOfferException.class})
    public ResponseEntity<Object> handleInvalidOfferException(InvalidOfferException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ScheduleTransactionExistException.class})
    public ResponseEntity<Object> handleScheduleTransactionExistException(ScheduleTransactionExistException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({BankAccountNotExistException.class})
    public ResponseEntity<Object> handleBankNotAccountExistException(BankAccountNotExistException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({InvalidScheduleTransactionException.class})
    public ResponseEntity<Object> handleInvalidScheduleTransactionException(InvalidScheduleTransactionException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({TransactionException.class})
    public ResponseEntity<Object> handleTransactionException(TransactionException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({InvalidContractException.class})
    public ResponseEntity<Object> handleInvalidContractException(InvalidContractException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ContractNotFoundException.class})
    public ResponseEntity<Object> handleContractNotFoundException(ContractNotFoundException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ContractAlreadyValidatedException.class})
    public ResponseEntity<Object> handleContractAlreadyValidatedException(ContractAlreadyValidatedException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({PersonalDataNotFoundException.class})
    public ResponseEntity<Object> handlePersonalDataNotFoundException(PersonalDataNotFoundException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler({ConversionException.class})
    public ResponseEntity<Object> handleConversionException(ConversionException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler({NoContractException.class})
    public ResponseEntity<Object> handleNoContractException(NoContractException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler({StorageNotExistException.class})
    public ResponseEntity<Object> handleStorageNotExistException(StorageNotExistException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler({ItemStockException.class})
    public ResponseEntity<Object> handleItemStockException(ItemStockException exception) {
        exception.printStackTrace(System.out);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        LinkedHashMap<String, Object> errorsMap = new LinkedHashMap<>();
        for (ObjectError err : errors) {
            errorsMap.put(((FieldError) err).getField(), err.getDefaultMessage());
        }
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }
}
