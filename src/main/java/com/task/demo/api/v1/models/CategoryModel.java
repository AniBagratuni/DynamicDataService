package com.task.demo.api.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Boolean isChild = false;

    @JsonProperty("fields")
    private List<FieldModel> fieldModels = new ArrayList<>();

    public CategoryModel(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("isChild")
    public Boolean getChild() {
        return isChild;
    }

    @JsonProperty("isChild")
    public void setChild(Boolean child) {
        isChild = child;
    }

    public List<FieldModel> getFieldModels() {
        return fieldModels;
    }

    public void setFieldModels(List<FieldModel> fieldModels) {
        this.fieldModels = fieldModels;
    }
}
