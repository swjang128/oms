package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oms.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu>{
}
