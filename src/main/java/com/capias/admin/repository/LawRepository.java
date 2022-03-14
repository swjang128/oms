package com.capias.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capias.admin.entity.Law;

public interface LawRepository extends JpaRepository<Law, Long>{
}
