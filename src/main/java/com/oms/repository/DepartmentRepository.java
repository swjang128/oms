package com.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oms.entity.Department;
import com.oms.entity.Department.UseYn;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department>{
	List<Department> findByUseYn(UseYn useYn);	// 사용하는 부서만 조회
}
