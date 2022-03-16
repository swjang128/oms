package com.oms.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.admin.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
