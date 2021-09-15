package com.task.demo.api.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.demo.enums.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class FieldModel {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Type type;

    private String subCategory;

    @JsonProperty("validations")
    private List<ValidationModel> validationModels = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public List<ValidationModel> getValidationModels() {
        return validationModels;
    }

    public void setValidationModels(List<ValidationModel> validationModels) {
        this.validationModels = validationModels;
    }
}
