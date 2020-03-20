package ru.mystore.store.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.mystore.store.exceptions.InternalServiceException;
import ru.mystore.store.exceptions.ProductNotFoundException;


@Slf4j
@ControllerAdvice
public class ShopExceptionHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(final ProductNotFoundException ex, Model model) {
        log.error("Page not found", ex);
        model.addAttribute("errorType", "404");
        model.addAttribute("errorDesc", "Страница не найдена!");
        return "error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServiceException.class)
    public String handleProductNotFoundException(final InternalServiceException ex, Model model) {
        log.error("Service error", ex);
        model.addAttribute("errorType", "500");
        model.addAttribute("errorDesc", "Сервис недоступен");
        return "error";
    }

}