package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Stock;
import me.chonchol.androposweb.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/stock/available/{quantity}")
    public List<Stock> getAvailableStockList(@PathVariable("quantity") Integer quantity){
        return stockRepository.getAvailableStockList(quantity);
    }

    @GetMapping("/api/stock/alert/{quantity}")
    public List<Stock> getStockAlertStockList(@PathVariable("quantity") Integer quantity){
        return stockRepository.getStockAlertStockList(quantity);
    }

    @GetMapping("api/report/stock/{from_date}/{to_date}")
    public List<Stock> getStockReportByDate(@PathVariable("from_date") String fromDate, @PathVariable("to_date") String toDate){
        return stockRepository.getStockReportByDate(fromDate, toDate);
    }

    @Transactional
    @ResponseBody
    @PutMapping("/api/stock/{product_id}/{quantity}")
    public void updateStockByProductId(@PathVariable("product_id") Integer productId, @PathVariable("quantity") Integer quantity){
        stockRepository.updateStockByProductId(productId, quantity);
    }
}
