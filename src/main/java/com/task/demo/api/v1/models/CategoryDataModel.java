package com.task.demo.api.v1.models;


import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryDataModel {

    @NotNull
    @NotBlank
    private String categoryName;

    @NotNull
    @NotBlank
    private String categoryData;

    public CategoryDataModel(){}

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(String categoryData) {
        this.categoryData = categoryData;
    }
}
