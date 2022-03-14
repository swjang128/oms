package com.capias.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capias.admin.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
