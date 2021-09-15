package com.task.demo.service;

import com.task.demo.api.v1.models.CategoryModel;

import java.util.List;

public interface CategoryService {

    void createCategory(List<CategoryModel> categoryModels);
}
