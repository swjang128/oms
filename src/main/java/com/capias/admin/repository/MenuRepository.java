package com.capias.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capias.admin.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>{
	List<Menu> findByParentId(long categoryId);		// 하위 메뉴 조회	 
}
