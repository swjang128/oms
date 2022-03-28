package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.Department;
import com.oms.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	List<Position> findByUseYn(int useYn);	// 사용하는 직급만 조회
}
