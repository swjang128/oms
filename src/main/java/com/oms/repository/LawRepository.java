package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.Law;

public interface LawRepository extends JpaRepository<Law, Long>{
}
