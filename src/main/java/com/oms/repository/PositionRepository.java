package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oms.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long>, JpaSpecificationExecutor<Position>{
}
