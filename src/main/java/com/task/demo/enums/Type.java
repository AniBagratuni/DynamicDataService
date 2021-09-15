package com.task.demo.enums;

public enum Type {
    IDENTITY("int"),
    INTEGER("int"),
    FLOAT("FLOAT"),
    STRING("TEXT"),
    DATETIME("DATETIME"),
    DATE("DATE"),
    SUB_CATEGORY("int");

    public final String sqlValue;

    Type(String sqlValue){
        this.sqlValue = sqlValue;
    }
}

