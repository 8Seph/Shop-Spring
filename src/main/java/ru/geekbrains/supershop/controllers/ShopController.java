package ru.geekbrains.supershop.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import ru.geekbrains.paymentservice.Payment;
import ru.geekbrains.supershop.beans.Cart;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.persistence.entities.Shopuser;
import ru.geekbrains.supershop.services.ProductService;

import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;
import ru.geekbrains.supershop.utils.CaptchaGenerator;
import ru.geekbrains.supershop.utils.Validators;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final Cart cart;
    private final CaptchaGenerator captchaGenerator;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final ShopuserService shopuserService;

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model,
                        @RequestParam(required = false) Integer category,
                        @RequestParam(required = false) Integer minPrice,
                        @RequestParam(required = false) Integer maxPrice,
                        @RequestParam(required = false) Boolean notAvailable) {
        model.addAttribute("cart", cart.getCartRecords());
        model.addAttribute("products", productService.findAll(category, minPrice, maxPrice, notAvailable));
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        if (data != null) {
            System.out.println(data);
        }

        model.addAttribute("products", productService.findAll(null));

        return "admin";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, @CookieValue(value = "data", required = false) String data, Principal principal) {

        if (principal == null) {
            return "redirect:/";
        }

        Shopuser shopuser = shopuserService.findByPhone(principal.getName());

        model.addAttribute("reviews", reviewService.getReviewsByShopuser(shopuser).orElse(new ArrayList<>()));
        model.addAttribute("shopuser", shopuser);
        if (data != null) {
            System.out.println(data);
        }

        return "profile";
    }

    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] captcha(HttpSession session) {
        try {
            BufferedImage img = captchaGenerator.getCaptchaImage();
            session.setAttribute("captchaCode", captchaGenerator.getCaptchaString());
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(img, "png", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/checkout")
    public String proceedToCheckout(String paymentId, Model model) {

        Payment payment = cart.getPayments()
                .stream()
                .filter(p -> p.getId() == Integer.valueOf(paymentId))
                .collect(Validators.toSingleton());

        cart.setPayment(payment);

        model.addAttribute("cart", cart);

        return "checkout";

    }

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getAllJsonProducts() {
        return new ResponseEntity<>(productService.findAll(null), HttpStatus.OK);
    }


    @PostMapping(value = "/forTest", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody String create(Integer id, String note) {
        if(id == null){
            id = 666;
            note = "test";
        }

        productService.addNote(id, note);
        return productService.getNote(id);
    }


}