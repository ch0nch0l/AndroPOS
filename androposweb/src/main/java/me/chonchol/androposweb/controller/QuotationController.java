package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Quotation;
import me.chonchol.androposweb.repository.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuotationController {

    @Autowired
    QuotationRepository quotationRepository;

    @GetMapping("/quotation")
    public List<Quotation> index(){
        return quotationRepository.findAll();
    }

    @GetMapping("/quotation/{quotation_id}")
    public Quotation show(@PathVariable("quotation_id") Integer id){
        return quotationRepository.getOne(id);
    }

    @PostMapping("/quotation")
    @ResponseBody
    public Quotation create(@RequestBody Quotation quotation){
        return quotationRepository.save(quotation);
    }

    @PutMapping("/quotation/{quotation_id}")
    public Quotation update(@PathVariable("quotation_id") Integer id, @RequestBody Quotation quotation){
        Quotation newQuotation = quotationRepository.getOne(id);
        newQuotation = quotation;
        return quotationRepository.save(newQuotation);
    }

    @DeleteMapping("/quotation/{quotation_id}")
    public Boolean delete(@PathVariable("quotation_id") Integer id){
        quotationRepository.deleteById(id);
        return true;
    }
}
