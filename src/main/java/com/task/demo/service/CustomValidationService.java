package com.task.demo.service;

import com.task.demo.dao.models.CustomValidation;

import java.util.List;
import java.util.Map;

public interface CustomValidationService {

    void addValidations(List<CustomValidation> customValidation);

    Map<String, String> getValidationsByCategory(String categoryName);

    boolean doCustomValidation(Object value, String logic);
}
