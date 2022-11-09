package com.github.moinmarcell.springshop.controller;

import com.github.moinmarcell.springshop.model.Order;
import com.github.moinmarcell.springshop.model.Product;
import com.github.moinmarcell.springshop.repository.OrderRepository;
import com.github.moinmarcell.springshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DirtiesContext
    void getProducts_whenProductListExist() throws Exception {
        mvc.perform(get("/shop/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getProduct_whenProductIdExist() throws Exception {
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
    void getProduct_whenProductIdNotExist() throws Exception {

        mvc.perform(get("/shop/products/{id}", 0))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DirtiesContext
    void addProduct_whenProductIsAddedToTheRepository() throws Exception {
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
    void getOrders_whenOrdersListExist() throws Exception {
        mvc.perform(get("/shop/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getOrder_whenOrderIdExistGetOrder() throws Exception {
        orderRepository.list().add(new Order("1", Collections.emptyList()));
        mvc.perform(get("/shop/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                    "id": "1",
                                    "products": []
                                }
                                """
                ));
    }

    @Test
    @DirtiesContext
    void getOrder_whenOrderIdNotExist() throws Exception{
        mvc.perform(get("/shop/orders/{id}", 0))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    @DirtiesContext
    void addOrder_whenOrderIsAddedToOrdersListGetOrder() throws Exception {
        mvc.perform(post("/shop/orders/addorder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                "id": "1",
                                "products": []
                                }
                                """
                ));
    }

    @Test
    @DirtiesContext
    void deleteOrder_whenOrderIdExistGetDeletedOrder() throws Exception {
        orderRepository.list().add(new Order("1", Collections.emptyList()));

        mvc.perform(delete("/shop/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                    "id": "1",
                                    "products": []
                                }
                                """
                ));
    }
}