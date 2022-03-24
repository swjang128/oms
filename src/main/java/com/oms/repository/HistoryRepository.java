package com.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long>{
}
