package com.task.demo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

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
        try {
            genericRepository.save(getValidData(categoryDataModel));
        } catch (Exception e) {
            throw new BadRequestException("Add Category data", e);
        }
    }

    @Override
    public void updateCategoryData(CategoryDataModel categoryDataModel) {
        try {
            genericRepository.update(getValidData(categoryDataModel));
        } catch (Exception e) {
            throw new BadRequestException("Update Category data", e);
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
        if (categoryData == null) {
            throw new ResourceNotFoundException(categoryName, "id", id);
        }
        return categoryData;
    }

    private Class<?> getClassByName(String className) {
        try {
            return Class.forName(applicationConfig.getPackagePrefix() + className);
        } catch (ClassNotFoundException e) {
            throw new ResourceNotFoundException("Category", className, "");
        }
    }

    private Object getValidData(CategoryDataModel categoryDataModel) throws JsonProcessingException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException, ClassNotFoundException {
        String categoryName = categoryDataModel.getCategoryName();
        Class<?> cls = getClassByName(categoryName);
        Object obj = objectMapper.readValue(categoryDataModel.getCategoryData(), cls);


        Map<Class, List> dynamicModels = new HashMap<>();
        List<Object> thisObject = new ArrayList<>(1);
        thisObject.add(obj);
        dynamicModels.put(cls, thisObject);
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if (applicationConfig.getPackagePrefix().equals(fieldType.getPackageName())) {
                List objects = dynamicModels.getOrDefault(fieldType, new ArrayList());
                objects.add(field.get(obj));
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                String genericTypeName = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName();
                if (genericTypeName.startsWith(applicationConfig.getPackagePrefix())) {
                    Collection collection = (Collection) field.get(obj);
                    if (collection != null) {
                        List objects = dynamicModels.getOrDefault(Class.forName(genericTypeName), new ArrayList());
                        objects.addAll(collection);
                    }
                }
            }
        }


        for (Map.Entry<Class, List> classListEntry : dynamicModels.entrySet()) {
            validateData(classListEntry.getKey(), classListEntry.getValue());
        }

        return obj;
    }

    private <T> void validateData(Class<T> clazz, List<T> objects) throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        var customValidationMap = customValidationService.getValidationsByCategory(clazz.getSimpleName());

        for (T obj : objects) {
            for (Map.Entry<String, String> validationRule : customValidationMap.entrySet()) {
                String validationFunction = validationRule.getValue();
                String fieldName = validationRule.getKey();
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                String fieldValue = objectMapper.writeValueAsString(field.get(obj));
                if (!customValidationService.doCustomValidation(fieldValue, validationFunction)) {
                    throw new IllegalArgumentException("Field validation failed for " + fieldName);
                }
            }
        }
    }

    /*public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Class<?> cls = Household.class;
        Object obj = objectMapper.readValue("{\"householdId\":1, \"createdUserId\":10, \"address\": \"AM\", \"members\": [{\"personId\": 100, \"first_name\": \"Po\"}, {\"personId\": 101, \"first_name\": \"Po1\"}]}", cls);


        Map<Class, List> dynamicModels = new HashMap<>();
        List<Object> thisObject = new ArrayList<>(1);
        thisObject.add(obj);
        dynamicModels.put(cls, thisObject);
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            if ("com.task.demo.dao.models.dynamic".equals(fieldType.getPackageName())) {
                List objects = dynamicModels.getOrDefault(fieldType, new ArrayList());
                objects.add(field.get(obj));
                dynamicModels.put(fieldType, objects);
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                String genericTypeName = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName();
                if (genericTypeName.startsWith("com.task.demo.dao.models.dynamic")) {
                    Collection collection = (Collection) field.get(obj);
                    if (collection != null) {
                        Class<?> aClass = Class.forName(genericTypeName);
                        List objects = dynamicModels.getOrDefault(aClass, new ArrayList());
                        objects.addAll(collection);
                        dynamicModels.put(aClass, objects);
                    }
                }
            }
        }

        dynamicModels.entrySet().forEach(e -> System.out.println(e));

    }*/

}
