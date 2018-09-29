package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Stock;
import me.chonchol.androposweb.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {


    @Autowired
    StockRepository stockRepository;

    @GetMapping("/stock")
    public List<Stock> index(){
        return stockRepository.findAll();
    }

    @GetMapping("/stock/{stock_id}")
    public Stock show(@PathVariable("stock_id") Integer id){
        return stockRepository.getOne(id);
    }

    @PostMapping("/stock")
    @ResponseBody
    public Stock create(@RequestBody Stock stock){
        return stockRepository.save(stock);
    }

    @PutMapping("/stock/{stock_id}")
    public Stock update(@PathVariable("stock_id") Integer id, @RequestBody Stock stock){
        Stock newStock = stockRepository.getOne(id);
        newStock = stock;
        return stockRepository.save(newStock);
    }

    @DeleteMapping("/stock/{stock_id}")
    public Boolean delete(@PathVariable("stock_id") Integer id){
        stockRepository.deleteById(id);
        return true;
    }
}
