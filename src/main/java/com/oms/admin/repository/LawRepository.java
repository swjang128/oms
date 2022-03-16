package com.oms.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.admin.entity.Law;

public interface LawRepository extends JpaRepository<Law, Long>{
}
