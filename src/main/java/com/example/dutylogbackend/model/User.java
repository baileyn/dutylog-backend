package com.example.dutylogbackend.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.dutylogbackend.model.security.Authority;

import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User extends AuditModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min = 4, max = 50)
	@Column(unique = true, length = 50)
	private String username;
	
	@NotNull
	@Size(min = 6, max = 100)
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String email;

	@NotNull
	@Column
	private boolean enabled = true;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column
	private Date lastPasswordResetDate;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private List<Authority> authorities;
	
	@Column
	private int access = 0;
}
