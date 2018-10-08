package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Subcategory;
import me.chonchol.androposweb.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubcategoryController {

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @GetMapping("/subcategory")
    public List<Subcategory> index(){
        return subcategoryRepository.findAll();
    }

//    @GetMapping("subcategory/{cat_id}")
//    public List<Subcategory> getSubCategoryByCatId(@PathVariable("cat_id") Integer id){
//        return subcategoryRepository.findAll();
//    }

    @GetMapping("subcategory/cat/{cat_id}")
    public List<Subcategory> getSubCategoryByCatId(@PathVariable("cat_id") Integer id){
        return subcategoryRepository.getSubCategoryByCatId(id);
    }

    @GetMapping("/subcategory/{subcat_id}")
    public Subcategory show(@PathVariable("subcat_id") Integer id){
        return subcategoryRepository.getOne(id);
    }

    @PostMapping("/subcategory")
    @ResponseBody
    public Subcategory create(@RequestBody Subcategory subcategory){
        return subcategoryRepository.save(subcategory);
    }

    @PutMapping("/subcategory/{subcat_id}")
    public Subcategory update(@PathVariable("subcat_id") Integer id, @RequestBody Subcategory subcategory){
        Subcategory newsubCategory = subcategoryRepository.getOne(id);
        newsubCategory = subcategory;
        return subcategoryRepository.save(newsubCategory);
    }

    @DeleteMapping("/subcategory/{subcat_id}")
    public Boolean delete(@PathVariable("subcat_id") Integer id){
        subcategoryRepository.deleteById(id);
        return true;
    }

}
