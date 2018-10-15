package me.chonchol.androposweb.controller;

import me.chonchol.androposweb.entity.Category;
import me.chonchol.androposweb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/api/category")
    public List<Category> index(){
        return categoryRepository.findAll();
    }

    @GetMapping("/api/category/{cat_id}")
    public Category show(@PathVariable("cat_id") Integer id){
        return categoryRepository.getOne(id);
    }

    @PostMapping("/api/category")
    @ResponseBody
    public Category create(@RequestBody Category category){
        return categoryRepository.save(category);
    }

    @PutMapping("/api/category/{cat_id}")
    public Category update(@PathVariable("cat_id") Integer id, @RequestBody Category category){
        Category newCategory = categoryRepository.getOne(id);
        newCategory = category;
        return categoryRepository.save(newCategory);
    }

    @DeleteMapping("/api/category/{cat_id}")
    public Boolean delete(@PathVariable("cat_id") Integer id){
        categoryRepository.deleteById(id);
        return true;
    }
}
