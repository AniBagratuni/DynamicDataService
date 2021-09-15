package com.task.demo.dao.models.dynamic;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.List;

import java.util.Date;

import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class Person { 
@Id
private int personId;
@Pattern(regexp="[a-zA-z]+")
private String first_name;
@Pattern(regexp="[a-zA-z]+")
private String last_name;
@NotNull
private int age;
}