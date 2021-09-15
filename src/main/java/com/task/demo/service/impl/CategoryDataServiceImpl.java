package com.task.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.demo.api.v1.models.CategoryDataModel;
import com.task.demo.dao.repositories.GenericRepository;
import com.task.demo.exceptions.BadRequestException;
import com.task.demo.exceptions.ResourceNotFoundException;
import com.task.demo.properties.ApplicationConfig;
import com.task.demo.service.CategoryDataService;
import com.task.demo.service.CustomValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryDataServiceImpl implements CategoryDataService {

    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GenericRepository genericRepository;

    @Autowired
    CustomValidationService customValidationService;

    @Override
    public void createCategoryData(CategoryDataModel categoryDataModel) {
        String categoryName = categoryDataModel.getCategoryName();
        try {
            Class<?> cls = getClassByName(categoryName);
            Object obj = objectMapper.readValue(categoryDataModel.getCategoryData(), cls);

            if(!checkValidation(categoryDataModel)){
                return;
            }

            genericRepository.save(obj);
        } catch (JsonMappingException ex) {
            throw new BadRequestException("Add Category data", ex);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("Add Category data", ex);
        }
    }

    @Override
    public void updateCategoryData(CategoryDataModel categoryDataModel) {
        String categoryName = categoryDataModel.getCategoryName();
        try {
            Class<?> cls = getClassByName(categoryName);
            Object obj = objectMapper.readValue(categoryDataModel.getCategoryData(), cls);

            if(!checkValidation(categoryDataModel)){
                return;
            }

            genericRepository.update(obj);
        } catch (JsonMappingException ex) {
            throw new BadRequestException("Update Category data", ex);
        } catch (JsonProcessingException ex) {
            throw new BadRequestException("Update Category data", ex);
        }
    }

    @Override
    public void deleteCategoryData(String categoryName, int id) {
        Class<?> cls = getClassByName(categoryName);
        genericRepository.delete(cls, id);
    }

    @Override
    public Object getCategoryDataById(String categoryName, int id) {
        Class<?> cls = getClassByName(categoryName);
        Object categoryData = genericRepository.findById(cls, id);
        if(categoryData == null){
            throw new ResourceNotFoundException(categoryName, "id", id);
        }
        return categoryData;
    }

    private Class getClassByName(String className) {
        try {
            Class<?> cls = Class.forName(applicationConfig.getPackagePrefix() + className);
            return cls;
        } catch (ClassNotFoundException e) {
            throw new ResourceNotFoundException("Category", className, "");
        }
    }

    private boolean checkValidation(CategoryDataModel categoryDataModel) {

        boolean[] status = {true};
        try {
            JsonNode jsonNode = objectMapper.readTree(categoryDataModel.getCategoryData());

            var customValidationMap = customValidationService.getValidationsByCategory(categoryDataModel.getCategoryName());

            jsonNode.fieldNames().forEachRemaining(fieldName -> {
                if (customValidationMap.containsKey(fieldName)) {
                    String validationFunction = customValidationMap.get(fieldName);
                    status[0] = status[0] && customValidationService.doCustomValidation(jsonNode.get(fieldName), validationFunction);
                }
            });

        } catch (JsonProcessingException ex) {
            throw new BadRequestException("Action failed", ex);
        }

        return status[0];
    }
}
