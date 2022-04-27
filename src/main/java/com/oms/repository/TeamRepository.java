package com.oms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.Task;
import com.oms.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
	Optional<Team> findByName(String name);		 
}
