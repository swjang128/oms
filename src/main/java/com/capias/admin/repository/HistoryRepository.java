package com.capias.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capias.admin.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long>{
}
