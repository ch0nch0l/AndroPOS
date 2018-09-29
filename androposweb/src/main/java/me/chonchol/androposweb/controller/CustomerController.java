package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Customers;
import me.chonchol.androposweb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customers> index(){
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{customer_id}")
    public Customers show(@PathVariable("customer_id") Integer id){
        return customerRepository.getOne(id);
    }

    @PostMapping("/customers")
    @ResponseBody
    public Customers create(@RequestBody Customers customer){
        return customerRepository.save(customer);
    }

    @PutMapping("/customers/{customer_id}")
    public Customers update(@PathVariable("customer_id") Integer id, @RequestBody Customers customer){
        Customers newCustomer = customerRepository.getOne(id);
        newCustomer = customer;
        return customerRepository.save(newCustomer);
    }

    @DeleteMapping("/customers/{customer_id}")
    public Boolean delete(@PathVariable("customer_id") Integer id){
        customerRepository.deleteById(id);
        return true;
    }
}
