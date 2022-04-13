package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
	List<Department> findByUseYn(Integer useYn);	// 사용하는 부서만 조회
}
