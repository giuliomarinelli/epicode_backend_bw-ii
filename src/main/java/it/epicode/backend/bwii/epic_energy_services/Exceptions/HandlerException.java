package it.epicode.backend.bwii.epic_energy_services.Exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(NotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "Not found", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerException(Exception e) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e.getMessage());
    }


    public static void exception(BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList()
                    .toString());
        }
    }
    public static void notFoundException(BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new NotFoundException(bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList()
                    .toString());
        }
    }

}
