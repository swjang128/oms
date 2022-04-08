package com.oms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long>{
	List<Menu> findByParentId(long categoryId);		// 하위 메뉴 조회	 
}
