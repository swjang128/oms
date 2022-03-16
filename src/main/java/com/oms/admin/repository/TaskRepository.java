package com.oms.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.admin.entity.Menu;
import com.oms.admin.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	List<Task> findByParentId(long categoryId);		// 하위 메뉴 조회	 
}
