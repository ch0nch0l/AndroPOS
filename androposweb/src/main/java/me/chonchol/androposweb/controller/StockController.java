package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Stock;
import me.chonchol.androposweb.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import java.util.List;

@RestController
public class StockController extends BaseController{


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
//    @GetMapping("/api/stock/available")
//    public List<Stock> getAvailableStockList(){
//        Query query = entityManager.createNativeQuery("SELECT * FROM STOCK A WHERE A.quantity > 0");
//        return query.getResultList();
//    }

    @GetMapping("/api/stock/available/{quantity}")
    public List<Stock> getAvailableStockList(@PathVariable("quantity") Integer quantity){
        return stockRepository.getAvailableStockList(quantity);
    }
}
