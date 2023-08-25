package ntq.uet.server.advice;

import java.util.Date;

import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.common.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import ntq.uet.server.model.payload.BasePageResponse;
import ntq.uet.server.model.payload.ErrorMessage;

@ControllerAdvice
public class HandlerExceptionAdvice {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        ErrorMessage.StatusCode.NOT_FOUND.code,
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.OK);
  }

  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<BasePageResponse<?>> globalExceptionHandler(GlobalException ex, WebRequest request) {
    BasePageResponse<?> response = new BasePageResponse<>(ex, request);
    
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}