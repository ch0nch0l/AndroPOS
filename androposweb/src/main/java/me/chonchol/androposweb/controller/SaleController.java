package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Sale;
import me.chonchol.androposweb.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SaleController {


    @Autowired
    SaleRepository saleRepository;

    @GetMapping("/api/sale")
    public List<Sale> index(){
        return saleRepository.findAll();
    }

    @GetMapping("/api/sale/{sale_id}")
    public Sale show(@PathVariable("sale_id") Integer id){
        return saleRepository.getOne(id);
    }

    @PostMapping("/api/sale")
    @ResponseBody
    public Sale create(@RequestBody Sale sale){
        return saleRepository.save(sale);
    }

    @PutMapping("/api/sale/{sale_id}")
    public Sale update(@PathVariable("sale_id") Integer id, @RequestBody Sale sale){
        Sale newSale = saleRepository.getOne(id);
        newSale = sale;
        return saleRepository.save(newSale);
    }

    @DeleteMapping("/api/sale/{sale_id}")
    public Boolean delete(@PathVariable("sale_id") Integer id){
        saleRepository.deleteById(id);
        return true;
    }
}
