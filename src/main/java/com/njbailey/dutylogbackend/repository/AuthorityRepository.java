package com.njbailey.dutylogbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.njbailey.dutylogbackend.model.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
