package ru.mystore.store.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mystore.store.beans.Cart;
import ru.mystore.store.services.ProductService;


@Controller
@RequiredArgsConstructor
public class ShopController {

    private final Cart cart;
    private final ProductService productService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model, @RequestParam(required = false) Integer category) {
        model.addAttribute("cart", cart.getCartRecords());
        model.addAttribute("products", productService.findAll(category));
        return "index";
    }

    @RequestMapping(value = "/")
    public String orderByAvailable(Model model, @RequestParam Integer ava) {
        //TODO сделать фильтр, который будет выводить фильтровать продукты по доступности. Выводить все продукты, но при этом указывать какие из них в наличие, а какие нет.
        if (ava == 0) {
            model.addAttribute("products", productService.findAllOrderByAvailable());

        } else {
            model.addAttribute("products", productService.findAllOrderByAvailableDesc());
        }

        return "index";
    }

}