package com.codemaster.exceptionhandler;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /*
        Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
    */

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = ex.getParameterName() + " parameter is missing";
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),errorMsg);
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
      Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
    */

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));

        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
    }

    /*
        Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     */

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex,WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND,ex.getMessage(),webRequest.getDescription(false));
        errorDetails.setMessage(ex.getMessage());
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        InvalidInputException triggered when invalid inputes passed to method
    */

    @ExceptionHandler(InvalidInputException.class)
    protected ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex,WebRequest webRequest)
    {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,ex.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     */

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = "Malformed JSON request";
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,errorMsg,ex.getLocalizedMessage());
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        Handle HttpMessageNotWritableException.
     */

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = "Error writing JSON Data";
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR,errorMsg,ex.getLocalizedMessage());
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        Handle NoHandlerFoundException.
     */

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        Handle javax.persistence.EntityNotFoundException
     */
    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND,"Unexcepted Error",ex.getLocalizedMessage());
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        ErrorDetails errorDetails;
        if (ex.getCause() instanceof ConstraintViolationException) {
            errorDetails = new ErrorDetails(HttpStatus.CONFLICT,"Database Error",ex.getCause().getLocalizedMessage());
            return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
        }
        errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR,"Unexcepted Error",ex.getLocalizedMessage());
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }

    /*
        Handle Exception, handle generic Exception.class
     */

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        String errorMsg = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST,errorMsg,ex.getMessage());
        return new ResponseEntity<Object>(errorDetails, new HttpHeaders(), errorDetails.getStatus());
    }
}
