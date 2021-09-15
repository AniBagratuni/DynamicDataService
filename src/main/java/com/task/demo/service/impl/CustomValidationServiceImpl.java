package com.task.demo.service.impl;

import com.task.demo.dao.models.CustomValidation;
import com.task.demo.dao.repositories.CustomValidationRepository;
import com.task.demo.exceptions.BadRequestException;
import com.task.demo.service.CustomValidationService;
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomValidationServiceImpl implements CustomValidationService {

    @Autowired
    private CustomValidationRepository customValidationRepository;

    @Override
    public void addValidations(List<CustomValidation> customValidationList) {
        customValidationRepository.saveAll(customValidationList);
    }

    @Override
    public Map<String, String> getValidationsByCategory(String categoryName) {
        return customValidationRepository.findByCategoryName(categoryName)
                .stream()
                .collect(Collectors.toMap(CustomValidation::getFieldName, CustomValidation::getValue));
    }

    @Override
    public boolean doCustomValidation(Object value, String logic) {
        return executeCustomValidation(value, logic);
    }

    private static boolean executeCustomValidation(Object value, String snippet) {
        try {
            JShell jshell = JShell.create();
            jshell.eval(snippet);

            String methodName = jshell.methods().collect(Collectors.toList()).get(0).name();
            System.out.println("methodName is " + methodName);

            String methodCall = methodName + "(" + value + ")";
            System.out.println("methodCall is " + methodCall);

            List<SnippetEvent> result = jshell.eval(methodCall);
            return Boolean.parseBoolean(result.get(0).value());
        } catch (Exception ex) {
            throw new BadRequestException("Add Category data", ex);
        }
    }
}
