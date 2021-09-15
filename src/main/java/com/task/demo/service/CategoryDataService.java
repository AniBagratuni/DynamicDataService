package com.task.demo.service;

import com.task.demo.api.v1.models.CategoryDataModel;

public interface CategoryDataService {

    void createCategoryData(CategoryDataModel categoryDataModel);

    void updateCategoryData(CategoryDataModel categoryDataModel);

    void deleteCategoryData(String name, int id);

    Object getCategoryDataById(String name, int id);
}
