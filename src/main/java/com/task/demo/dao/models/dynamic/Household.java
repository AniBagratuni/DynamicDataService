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
public class Household { 
@Id
private int householdId;
private int createdUserId;
private int updatedUserId;
@Column(nullable = true, updatable = false)
@Temporal(TemporalType.TIMESTAMP)
@CreatedDate
private Date dateCreated = new Date();
private Long dateUpdated;
@NotNull
private String address;
@OneToMany(fetch = FetchType.EAGER, orphanRemoval=true, cascade = CascadeType.ALL)
private List<Person> members;
}