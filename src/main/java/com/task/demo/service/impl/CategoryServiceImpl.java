package com.task.demo.service.impl;

import com.task.demo.api.v1.models.CategoryModel;
import com.task.demo.api.v1.models.FieldModel;
import com.task.demo.api.v1.models.ValidationModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.task.demo.dao.models.CustomValidation;
import com.task.demo.exceptions.BadRequestException;
import com.task.demo.properties.ApplicationConfig;
import com.task.demo.service.CategoryService;
import com.task.demo.service.CustomValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ApplicationConfig applicationConfig;

    @Autowired
    CustomValidationService customValidationService;

    List<CustomValidation> customValidationList = new ArrayList<>();

    public void createCategory(List<CategoryModel> categoryModels) {
        try {
            for (var categoryModel : categoryModels) {
                FileWriter fileWriter = initClassFile(categoryModel.getName());

                for (var field : categoryModel.getFieldModels()) {
                    if (field.getValidationModels() != null) {
                        createValidation(categoryModel.getName(), field, fileWriter);
                    }
                    switch (field.getType()) {
                        case IDENTITY:
                            fileWriter.write("@Id\n");
                            fileWriter.write("private int " + field.getName() + ";\n");
                            break;
                        case SUB_CATEGORY:
                            fileWriter.write("@OneToMany(fetch = FetchType.EAGER, orphanRemoval=true, cascade = CascadeType.ALL)\n");
                            fileWriter.write("private List<" + field.getSubCategory() + "> " + field.getName() + ";\n");
                            break;
                        case STRING:
                            fileWriter.write("private String " + field.getName() + ";\n");
                            break;
                        case INTEGER:
                            fileWriter.write("private int " + field.getName() + ";\n");
                            break;
                        case FLOAT:
                            fileWriter.write("private Float " + field.getName() + ";\n");
                            break;
                        case DATETIME:
                            fileWriter.write("private Long " + field.getName() + ";\n");
                            break;
                        case DATE:
                            fileWriter.write("@Column(nullable = true, updatable = false)\n");
                            fileWriter.write("@Temporal(TemporalType.TIMESTAMP)\n");
                            fileWriter.write("@CreatedDate\n");
                            fileWriter.write("private Date " + field.getName() + " = new Date();\n");
                    }
                }
                fileWriter.write("}");
                fileWriter.close();

            }
            log.info("The category models are created");
            customValidationService.addValidations(customValidationList);
        } catch (Exception e) {
            throw new BadRequestException("Create Category", e);
        }
    }

    private void createValidation(String categoryName, FieldModel field, FileWriter fileWriter) throws IOException {
        for (ValidationModel validationModel : field.getValidationModels()) {
            switch (validationModel.getType()) {
                case REGEXP:
                    fileWriter.write("@Pattern(regexp=\"" + validationModel.getValue() + "\")\n");
                    break;
                case REQUIRED:
                    fileWriter.write("@NotNull\n");
                    break;
                case CUSTOM:
                    var customValidation = new CustomValidation();
                    customValidation.setCategoryName(categoryName);
                    customValidation.setValue(validationModel.getValue());
                    customValidation.setFieldName(field.getName());
                    customValidationList.add(customValidation);
                    break;
            }
        }
    }

    private FileWriter initClassFile(String className) throws Exception {

        FileWriter fileWriter = new FileWriter(applicationConfig.getJavaPath() + className + ".java");

        fileWriter.write("package com.task.demo.dao.models.dynamic;\n\n");

        fileWriter.write("import org.springframework.data.annotation.CreatedDate;\n\n");
        fileWriter.write("import javax.persistence.*;\n");
        fileWriter.write("import javax.validation.constraints.*;\n\n");
        fileWriter.write("import java.util.List;\n\n");
        fileWriter.write("import java.util.Date;\n\n");
        fileWriter.write("import lombok.*;\n\n");

        //annotations
        fileWriter.write("@Entity\n");
        fileWriter.write("@AllArgsConstructor\n");
        fileWriter.write("@NoArgsConstructor\n");
        fileWriter.write("@ToString\n");
        fileWriter.write("@Setter\n");
        fileWriter.write("@Getter\n");

        //class
        fileWriter.write("public class " + className + " { \n");

        return fileWriter;
    }
}
