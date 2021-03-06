package art.alex.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ControllerAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    private static Map<?, ?> generateFieldExceptions(List<FieldError> errors) {
        return errors.stream().collect(Collectors.groupingBy(FieldError::getField, Collectors.mapping(FieldError::getDefaultMessage, Collectors.joining(", "))));
    }

    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<Map<?, ?>> handleBindingExceptions(BindException ex) {
        logger.debug("Validation exception.", ex);

        return new ResponseEntity<>(generateFieldExceptions(ex.getBindingResult().getFieldErrors()), BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<Map<?, ?>> handleException(MethodArgumentNotValidException ex) {
        logger.debug("Validation exception.", ex);

        return new ResponseEntity<>(generateFieldExceptions(ex.getBindingResult().getFieldErrors()), BAD_REQUEST);
    }
}