package com.example.vote.controller.exceptionHandler;

import com.example.vote.controller.exceptionHandler.dto.ErrorMsgDto;
import com.example.vote.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler
{
    @ExceptionHandler(DuplicateOptionsException.class)
    ResponseEntity DuplicationOptionsException(DuplicateOptionsException ex) {
        log.debug("handling exception::" + ex);

        return new ResponseEntity(new ErrorMsgDto(new Date(), "duplicate options in vote!",
                ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnAuthorizedActionException.class)
    ResponseEntity UnAuthorizedActionException(UnAuthorizedActionException ex) {
        log.debug("handling exception::" + ex);

        return new ResponseEntity(new ErrorMsgDto(new Date(), "unauthorized to perform action",
                ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity ResourceNotFoundException(ResourceNotFoundException ex) {
        log.debug("handling exception::" + ex);

        return new ResponseEntity(new ErrorMsgDto(new Date(), "the resource you requested cannot be found!",
                ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OptionNotExistException.class)
    ResponseEntity OptionNotExistException(OptionNotExistException ex) {
        log.debug("handling exception::" + ex);

        return new ResponseEntity(new ErrorMsgDto(new Date(), "optionId is not associated with vote!",
                ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(VoteIdAndGroupIdNotMatchingException.class)
    ResponseEntity VoteIdAndGroupIdNotMatchingException(VoteIdAndGroupIdNotMatchingException ex) {
        log.debug("handling exception::" + ex);

        return new ResponseEntity(new ErrorMsgDto(new Date(), "the group that you requested not have vote you requested!",
                ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler({WebExchangeBindException.class})
    ResponseEntity webExchangeBindException(WebExchangeBindException ex)
    {
        String rejectedValue = ex.getBindingResult().getFieldError().getRejectedValue() == null? ""
                : ex.getBindingResult().getFieldError().getRejectedValue().toString();
        String message = ex.getBindingResult().getFieldError().getDefaultMessage() == null? ""
                :ex.getBindingResult().getFieldError().getDefaultMessage();

        return new ResponseEntity(
                new ErrorMsgDto(new Date(), "bad request ", rejectedValue
                        + " is not valid with message : "
                        + message),
                HttpStatus.BAD_REQUEST
        );
    }
}
