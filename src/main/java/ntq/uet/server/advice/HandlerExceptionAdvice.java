package ntq.uet.server.advice;

import ntq.uet.server.common.exception.ErrorCode;
import ntq.uet.server.common.exception.GlobalException;
import ntq.uet.server.common.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import ntq.uet.server.common.base.BaseResponse;
import ntq.uet.server.common.base.Error;

@ControllerAdvice
public class HandlerExceptionAdvice {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Error> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    Error message = new Error(
        ErrorCode.NOT_FOUND.getCode(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<>(message, HttpStatus.OK);
  }

  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<BaseResponse<?>> globalExceptionHandler(GlobalException ex, WebRequest request) {
    BaseResponse<?> response = new BaseResponse<>(ex, request);
    
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}