package com.task.demo.properties;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class ApplicationConfig {

    @Value("${project.path}")
    private String projectPath;

    @Value("${generated.java.path}")
    private String generatedJavaPath;

    @Value("${generated.class.path}")
    private String generatedClassPath;

    @Value("${packagePrefix}")
    private String packagePrefix;

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getGeneratedJavaPath() {
        return generatedJavaPath;
    }

    public void setGeneratedJavaPath(String generatedJavaPath) {
        this.generatedJavaPath = generatedJavaPath;
    }

    public String getGeneratedClassPath() {
        return generatedClassPath;
    }

    public void setGeneratedClassPath(String generatedClassPath) {
        this.generatedClassPath = generatedClassPath;
    }

    public String getJavaPath() {
        return projectPath + generatedClassPath;
    }

    public String getPackagePrefix() {
        return packagePrefix;
    }

    public void setPackagePrefix(String packagePrefix) {
        this.packagePrefix = packagePrefix;
    }
}
