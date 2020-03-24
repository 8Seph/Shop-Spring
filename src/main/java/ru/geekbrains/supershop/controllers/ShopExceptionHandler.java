package ru.geekbrains.supershop.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.geekbrains.supershop.exceptions.ImageExtensionException;
import ru.geekbrains.supershop.exceptions.InternalServiceException;
import ru.geekbrains.supershop.exceptions.ProductNotFoundException;


@Slf4j
@ControllerAdvice //Для прослушивания исключений
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
    public String handleInternalServiceException(final InternalServiceException ex, Model model) {
        log.error("Service error", ex);
        model.addAttribute("errorType", "500");
        model.addAttribute("errorDesc", "Сервис недоступен");
        return "error";
    }


    @ExceptionHandler(ImageExtensionException.class)
    public String handleImageExtensionException(final InternalServiceException ex, Model model) {
        log.error("Service error", ex);
        model.addAttribute("errorType", "Ошибка");
        model.addAttribute("errorDesc", "Не верный формат файла");
        return "error";
    }

}