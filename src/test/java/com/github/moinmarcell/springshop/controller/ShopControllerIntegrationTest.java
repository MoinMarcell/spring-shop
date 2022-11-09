package com.github.moinmarcell.springshop.controller;

import com.github.moinmarcell.springshop.model.Product;
import com.github.moinmarcell.springshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void getProducts()
            throws Exception {
        try {
            mvc.perform(get("/shop/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getProduct() throws Exception {
        productRepository.list().add(new Product("Banane", "1"));

        mvc.perform(get("/shop/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                 "name": "Banane",
                                 "id": "1"
                                }
                                """
                ));
    }

    @Test
    void addProduct() throws Exception {
        productRepository.list().add(new Product("Banane", "id"));

        mvc.perform(post("/shop/products/addProduct"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                "name": "Banane",
                                "id": "1"
                                }
                                """
                ));
    }

    @Test
    void getOrders() {
    }

    @Test
    void getOrder() {
    }

    @Test
    void deleteOrder() {
    }

    @Test
    void addOrder() {
    }
}