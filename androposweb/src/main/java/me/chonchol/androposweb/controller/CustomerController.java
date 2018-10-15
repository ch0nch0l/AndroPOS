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

    @GetMapping("/api/customers")
    public List<Customers> index(){
        return customerRepository.findAll();
    }

    @GetMapping("/api/customers/{customer_id}")
    public Customers show(@PathVariable("customer_id") Integer id){
        return customerRepository.getOne(id);
    }

    @PostMapping("/api/customers")
    @ResponseBody
    public Customers create(@RequestBody Customers customer){
        return customerRepository.save(customer);
    }

    @PutMapping("/api/customers/{customer_id}")
    public Customers update(@PathVariable("customer_id") Integer id, @RequestBody Customers customer){
        Customers newCustomer = customerRepository.getOne(id);
        newCustomer = customer;
        return customerRepository.save(newCustomer);
    }

    @DeleteMapping("/api/customers/{customer_id}")
    public Boolean delete(@PathVariable("customer_id") Integer id){
        customerRepository.deleteById(id);
        return true;
    }
}
