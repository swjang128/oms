package com.oms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.dto.MenuDTO;
import com.oms.entity.Menu;
import com.oms.repository.MenuRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 메뉴 목록을 관리하는 Service
 * @author Capias J
 *
 */
@Service
@Slf4j
public class MenuService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	MenuRepository menuRepository;

	/** 
	 * 메뉴 목록 조회 (READ)
	 * @return List<MenuDTO>
	 */
	public List<MenuDTO> read() {
		// 메뉴 목록 조회
		List<Menu> menuList = menuRepository.findAll();
		List<MenuDTO> result = menuList.stream()											 
									 .map(MenuDTO::new)
									 .collect(Collectors.toList());
		
		log.info("###### MenuList Result: {}", result);
		
		return result;
	}
	
	/** 
	 * 상위 메뉴 조회 (READ)
	 * 메인 메뉴는 parentId를 0으로 받아서 조회한다
	 * @return List<MenuDTO>
	 */
//	public List<MenuDTO> readParent(Long menuId) {		
//		// 메뉴(대분류) 목록 조회
//		List<Menu> parentMenu = menuRepository.findByParentId(menuId);
//		List<MenuDTO> result = parentMenu.stream()											 
//				 .map(MenuDTO::new)
//				 .collect(Collectors.toList());
//		log.info("****** 소분류 메뉴: {}", result);
//		
//		return result;
//	}
	
	/** 
	 * 특정 ParentId를 가진 메뉴 조회 (READ)
	 * @return List<MenuDTO>
	 */
	public List<MenuDTO> read(Long menuId) {		
		// 메뉴(대분류) 목록 조회
		List<Menu> parentMenu = menuRepository.findByParentId(menuId);
		List<MenuDTO> result = parentMenu.stream()											 
				 .map(MenuDTO::new)
				 .collect(Collectors.toList());
		log.info("****** 소분류 메뉴: {}", result);
		
		return result;
	}
	
	/** 
	 * 메뉴 등록 (CREATE)
	 * @return 등록한 메뉴 정보
	 */
	@Transactional
	public Menu create(@RequestBody MenuDTO menuDTO) {
		// 메뉴 등록 (CREATE)
		Menu result = menuRepository.save(menuDTO.toEntity());

		return result;
	}
	
	/** 
	 * 메뉴 수정 (UPDATE)
	 * @param @RequestBody
	 * @return 
	 * @return 
	 */
	@Transactional
	public Integer update(@RequestBody MenuDTO menuDTO) {
		Long id = menuDTO.getId();		
		int result = 0;
		// 해당 메뉴이 있는지 확인
		menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴이 없습니다. id: "+id));
		
		try {
			// 메뉴 수정 (UPDATE)
			menuRepository.save(menuDTO.toEntity());
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * 메뉴 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Integer delete(List<Long> param) {
		int result = 0;
		
		// 메뉴 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				menuRepository.deleteById(param.get(p));
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
