package com.task.demo.dao.repositories;

import com.task.demo.dao.models.CustomValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomValidationRepository extends JpaRepository<CustomValidation, Long> {

    CustomValidation findByCategoryNameAndFieldName(String categoryName, String fieldName);

    List<CustomValidation> findByCategoryName(String categoryName);
}

