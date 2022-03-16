package com.oms.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.admin.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long>{
}
