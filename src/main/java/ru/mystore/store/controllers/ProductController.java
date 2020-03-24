package ru.mystore.store.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ru.mystore.store.exceptions.ProductNotFoundException;
import ru.mystore.store.persistence.entities.Image;
import ru.mystore.store.persistence.entities.Product;
import ru.mystore.store.services.ImageService;
import ru.mystore.store.services.ProductService;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ImageService imageService;
    private final ProductService productService;

    @GetMapping("/{id}")
    public String getOneProduct(Model model, @PathVariable String id) throws ProductNotFoundException {
        model.addAttribute("product", productService.findOneById(UUID.fromString(id)));
        return "product";
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable String id) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = imageService.loadProductImage(id);
        if (bufferedImage != null) {
            ImageIO.write(bufferedImage,"png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } else {
            return new byte[0];
        }
    }

    @PostMapping
    public String addProduct(@RequestParam("multipartFile") MultipartFile multipartFile, Product product) throws IOException {
        Image image = imageService.uploadProductImage(multipartFile, product);
        product.setAdded(new Date());
        product.setImage(image);
        return productService.save(product); //возвратит редирект
    }

}