package com.example.dutylogbackend.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name="todo_tasks")
@Data
public class TodoTask extends AuditModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(columnDefinition="text", nullable=false)
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private boolean completed = false;
}
