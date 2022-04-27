package com.oms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{		 
}
