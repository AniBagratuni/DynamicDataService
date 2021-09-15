package com.task.demo.api.v1.controllers;

import com.task.demo.api.v1.models.CategoryModel;
import com.task.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createMetaData(@RequestBody List<CategoryModel> metaDataModels) {

        categoryService.createCategory(metaDataModels);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
