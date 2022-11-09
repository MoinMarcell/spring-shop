package com.github.moinmarcell.springshop.controller;

import com.github.moinmarcell.springshop.model.Order;
import com.github.moinmarcell.springshop.model.Product;
import com.github.moinmarcell.springshop.service.ShopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        return shopService.listProducts();
    }

    @GetMapping(path = "/products/{id}")
    public Product getProduct(@PathVariable String id){
        return shopService.getProduct(id);
    }

    @PostMapping("/products/addProduct")
    public Product addProduct(@RequestBody Product product){
        /*Random random = new Random();
        int rand = random.nextInt(1000, 10000);
        String id = String.valueOf(rand);*/
        shopService.listProducts().add(product);
        return product;
    }

    @GetMapping("/orders")
    public List<Order> getOrders(){
        return shopService.listOrders();
    }

    @GetMapping(path = "/orders/{id}")
    public Order getOrder(@PathVariable String id){
        return shopService.getOrder(id);
    }

    @DeleteMapping(path = "/orders/{id}")
    public Order deleteOrder(@PathVariable String id){
        for(Order o : shopService.listOrders()){
            if(o.getId().equals(id)){
                shopService.listOrders().remove(o);
                return o;
            }
        }
        return null;
    }

    @PostMapping("/orders/addorder")
    public Order addOrder(@RequestBody List<String> ids){
        int counter = 1;
        String id = String.valueOf(counter);
        shopService.addOrder(id, ids);
        return shopService.addOrder(id, ids);
    }
}
