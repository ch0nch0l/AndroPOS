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

    @GetMapping("/api/stock")
    public List<Stock> index(){
        return stockRepository.findAll();
    }

    @GetMapping("/api/stock/{stock_id}")
    public Stock show(@PathVariable("stock_id") Integer id){
        return stockRepository.getOne(id);
    }

    @PostMapping("/api/stock")
    @ResponseBody
    public Stock create(@RequestBody Stock stock){
        return stockRepository.save(stock);
    }

    @PutMapping("/api/stock/{stock_id}")
    public Stock update(@PathVariable("stock_id") Integer id, @RequestBody Stock stock){
        Stock newStock = stockRepository.getOne(id);
        newStock = stock;
        return stockRepository.save(newStock);
    }

    @DeleteMapping("/api/stock/{stock_id}")
    public Boolean delete(@PathVariable("stock_id") Integer id){
        stockRepository.deleteById(id);
        return true;
    }

    //Native
    @GetMapping("/api/stock/available")
    public List<Stock> getAvailableStockList(){
        return stockRepository.getAvailableStockList();
    }
}
