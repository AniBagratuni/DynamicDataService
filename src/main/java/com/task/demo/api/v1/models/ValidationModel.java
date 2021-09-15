package com.task.demo.api.v1.models;

import com.task.demo.enums.Validation;

import javax.validation.constraints.NotNull;

public class ValidationModel {

    @NotNull
    private Validation type;

    private String value;

    public ValidationModel() {
    }

    public Validation getType() {
        return type;
    }

    public void setType(Validation type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
