package ru.geekbrains.supershop.persistence.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.supershop.beans.Cart;
import ru.geekbrains.supershop.controllers.ShopController;
import ru.geekbrains.supershop.persistence.entities.Product;
import ru.geekbrains.supershop.services.ProductService;
import ru.geekbrains.supershop.services.ReviewService;
import ru.geekbrains.supershop.services.ShopuserService;
import ru.geekbrains.supershop.utils.CaptchaGenerator;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ShopController.class)
public class ShopControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Cart cart;

    @MockBean
    CaptchaGenerator captchaGenerator;

    @MockBean
    ProductService productService;

    @MockBean
    ReviewService reviewService;

    @MockBean
    ShopuserService shopuserService;



    @Before
    public void setUp() {
        List<Product> products = new ArrayList<Product>() {{
            add(new Product());
            add(new Product());
        }};

        Map<Integer, String> notes = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            notes.put(i, "note" + i);
        }

        when(productService.findAll(null)).thenReturn(products);
    }

    @Test
    public void mustReturnAllproduct() throws Exception {
        mockMvc
                .perform(get("/json")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void mustReturnTestValue() throws Exception {
        mockMvc
                .perform(post("/forTest")
                        .contentType(MediaType.TEXT_HTML_VALUE)
                        .param("id", "5"))
                .andExpect(status().isCreated());

    }

}
