package com.oms.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long>{
	List<History> findByStatus(Integer httpStatus);
}
