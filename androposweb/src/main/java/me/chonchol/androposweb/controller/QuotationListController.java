package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Quotation;
import me.chonchol.androposweb.entity.QuotationList;
import me.chonchol.androposweb.repository.QuotationListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuotationListController {
    @Autowired
    QuotationListRepository quotationListRepository;

    @GetMapping("/api/quotationlist")
    public List<QuotationList> index(){
        return quotationListRepository.findAll();
    }

    @GetMapping("/api/quotationlist/{quotelist_id}")
    public QuotationList show(@PathVariable("quotelist_id") Integer id){
        return quotationListRepository.getOne(id);
    }

    @PostMapping("/api/quotationlist")
    @ResponseBody
    public QuotationList create(@RequestBody QuotationList quotationList){
        return quotationListRepository.save(quotationList);
    }

    @PutMapping("/api/quotationlist/{quotelist_id}")
    public QuotationList update(@PathVariable("quotelist_id") Integer id, @RequestBody QuotationList quotationList){
        QuotationList newQuotationList = quotationListRepository.getOne(id);
        newQuotationList = quotationList;
        return quotationListRepository.save(newQuotationList);
    }

    @DeleteMapping("/api/quotationlist/{quotelist_id}")
    public Boolean delete(@PathVariable("quotelist_id") Integer id){
        quotationListRepository.deleteById(id);
        return true;
    }
}
