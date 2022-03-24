package com.oms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.Menu;
import com.oms.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	List<Task> findByParentId(long categoryId);		// 하위 메뉴 조회	 
}
