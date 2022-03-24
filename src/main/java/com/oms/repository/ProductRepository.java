package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
