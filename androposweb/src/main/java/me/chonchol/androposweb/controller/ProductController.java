package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Product;
import me.chonchol.androposweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Statement;
import java.util.List;

@RestController
public class ProductController {


    @Autowired
    ProductRepository productRepository;

    @GetMapping("/api/product")
    public List<Product> index(){
        return productRepository.findAll();
    }

    @GetMapping("/api/product/{product_id}")
    public Product show(@PathVariable("product_id") Integer id){
        return productRepository.getOne(id);
    }

    @PostMapping("/api/product")
    @ResponseBody
    public Product create(@RequestBody Product product){
        return productRepository.save(product);
    }

    @PutMapping("/api/product/{product_id}")
    public Product update(@PathVariable("product_id") Integer id, @RequestBody Product product){
        Product newProduct = productRepository.getOne(id);
        newProduct = product;
        return productRepository.save(newProduct);
    }

    @DeleteMapping("/api/product/{product_id}")
    public Boolean delete(@PathVariable("product_id") Integer id){
        productRepository.deleteById(id);
        return true;
    }
}
