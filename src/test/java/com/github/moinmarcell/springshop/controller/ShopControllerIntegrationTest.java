package com.github.moinmarcell.springshop.controller;

import com.github.moinmarcell.springshop.model.Product;
import com.github.moinmarcell.springshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class ShopControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DirtiesContext
    void getProducts() throws Exception {
        mvc.perform(get("/shop/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
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
    @DirtiesContext
    void addProduct() throws Exception {
        mvc.perform(post("/shop/products/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                        "name": "Banane",
                                        "id": "1"
                                        }
                                        """
                        ))
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
    @DirtiesContext
    void getOrders() throws Exception {
        mvc.perform(get("/shop/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
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