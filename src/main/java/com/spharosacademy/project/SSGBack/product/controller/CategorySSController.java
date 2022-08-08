package com.spharosacademy.project.SSGBack.product.controller;

import com.spharosacademy.project.SSGBack.product.dto.input.RequestCategorySSDto;
import com.spharosacademy.project.SSGBack.product.entity.CategorySS;
import com.spharosacademy.project.SSGBack.product.service.CategorySSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CateSS")
@RequiredArgsConstructor
public class CategorySSController {
    private final CategorySSService categorySSService;

    @PostMapping("/add")
    public CategorySS addCategorySS(@RequestBody RequestCategorySSDto categorySSDto) {
        return categorySSService.addCategorySS(categorySSDto);
    }

    @GetMapping("/getAll")
    public List<CategorySS> getAll() {
        return categorySSService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategorySSById(@PathVariable Integer id){
        categorySSService.deleteCategorySSById(id);
    }

    @GetMapping("/get/{id}")
    public CategorySS categorySS(@PathVariable Integer id) {
        return categorySSService.getCategorySSById(id);
    }

    @PutMapping("/edit")
    public CategorySS editCategorySS(@RequestBody RequestCategorySSDto categorySSDto) {
        return categorySSService.editCategorySS(categorySSDto);
    }
}