package com.task.demo.api.v1.controllers;

import com.task.demo.api.v1.models.CategoryDataModel;
import com.task.demo.service.CategoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categoryData")
public class CategoryDataController {

    @Autowired
    CategoryDataService categoryDataService;

    @PostMapping
    public ResponseEntity<Void> createCategoryData(@RequestBody CategoryDataModel dataModel) {
        categoryDataService.createCategoryData(dataModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCategoryData(@RequestBody CategoryDataModel dataModel) {
        categoryDataService.updateCategoryData(dataModel);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{categoryName}/{id}")
    public ResponseEntity<Object> getCategoryDataById(@PathVariable(value = "categoryName") String categoryName, @PathVariable(value = "id") int id) {
        Object data = categoryDataService.getCategoryDataById(categoryName, id);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{categoryName}/{id}")
    public ResponseEntity<Void> deleteCategoryData(@PathVariable(value = "categoryName") String categoryName, @PathVariable(value = "id") int id) {
        categoryDataService.deleteCategoryData(categoryName, id);
        return ResponseEntity.noContent().build();
    }
}
