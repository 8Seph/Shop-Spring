package ru.geekbrains.supershop.persistence.controllers;

import org.junit.Before;
import org.junit.Test;
import ru.geekbrains.supershop.beans.Cart;
import ru.geekbrains.supershop.controllers.ShopController;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.services.ProductService;
import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;
import ru.geekbrains.supershop.utils.CaptchaGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShopControllerTest {
    private ShopController shopController;
    private List<Product> products = new ArrayList<Product>() {{
        add(new Product());
        add(new Product());
    }};

    @Before
    public void setUp() {
        ProductService productService = mock(ProductService.class);
        Cart cart = mock(Cart.class);
        CaptchaGenerator captchaGenerator = mock(CaptchaGenerator.class);
        ReviewService reviewService = mock(ReviewService.class);
        ShopuserService shopuserService = mock(ShopuserService.class);
        shopController = new ShopController(cart, captchaGenerator, productService, reviewService, shopuserService);

        when(productService.findAll(null)).thenReturn(products);
    }

    @Test
    public void getAllJsonProductsTest(){
        List<Product> testProducts = shopController.getAllJsonProducts().getBody();
        assertEquals(2,testProducts.size());
    }
}
