package com.oms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oms.config.ResponseCode;
import com.oms.dto.MenuDTO;
import com.oms.entity.Menu;
import com.oms.repository.MenuRepository;
import com.oms.specification.MenuSpecification;

import lombok.extern.slf4j.Slf4j;

/**
 * 메뉴 Service
 * @author JSW
 */
@Service
@Slf4j
public class MenuService{
	@Autowired
	MenuRepository menuRepository;

	/** 
	 * 메뉴 등록 (CREATE)
	 * @param MenuDTO, Map<String, Object>
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> create(MenuDTO menuDTO, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.CREATED;
		Menu menu = null;
		// 메뉴 등록 (CREATE)
		try {
			menu = menuRepository.save(menuDTO.toEntity());
			resultMap.put("menu", menu);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}
	
	/** 
	 * 메뉴 목록 조회 (READ)
	 * @param Map<String, Object>, Map<String, Object>
	 * @return Map<String, Object>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
		List<Menu> menu = new ArrayList<Menu>();
		List<MenuDTO> menuDTO = new ArrayList<MenuDTO>();
		Object useYn = paramMap.get("useYn");
		Object url = paramMap.get("url");
		List<Long> depth = (List<Long>) paramMap.get("depth");
		Specification<Menu> specification = (root, query, criteriaBuilder) -> null;
		// Specification 설정
		if (useYn != null)
			specification = specification.and(MenuSpecification.findByUseYn(useYn));
		if (url != null) 
			specification = specification.and(MenuSpecification.findByUrl(url));		
		if (depth != null) {	// depth가 존재하면 depth를 조건으로 넣고, depth가 없으면 parent값이 null인 것으로 조건 변경
			specification = specification.and(MenuSpecification.findByDepth(depth));
		} else {
			specification = specification.and(MenuSpecification.parentIsNull());
		}
		// 메뉴 목록 조회
		try {
			menu = menuRepository.findAll(specification);
			menuDTO = menu.stream().map(MenuDTO::new).collect(Collectors.toList());			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		resultMap.put("menuList", menuDTO);
		return resultMap;
	}
	
	/** 
	 * 현재 페이지의 url, 메뉴명을 조회
	 * @param Map<String, Object>, Map<String, Object>
	 * @return Map<String, Object>
	 */
	public Map<String, Object> readServiceLocation(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
		String url = "";
		String menuName = "";
		Optional<Menu> menu = null;
		// Specification 설정
		Specification<Menu> specification = (root, query, criteriaBuilder) -> null;
		if (paramMap.get("url") != null) 
			specification = specification.and(MenuSpecification.findByUrl(paramMap.get("url")));
		// 메뉴가 존재하는지 확인 (없으면 공백으로 리턴)
		menu = menuRepository.findOne(specification);
		if (menu.isEmpty()) {
			result = ResponseCode.MENU_DOES_NOT_EXISTS;
			resultMap.put("result", result);
			resultMap.put("url", url);
			resultMap.put("menuName", menuName);
			return resultMap;
		}
		// 현재 서비스 위치 메뉴 정보 조회
		try {			
			menuName = menu.get().getName();
			url = menu.get().getUrl();
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		
		// 결과 리턴
		resultMap.put("result", result);
		resultMap.put("url", url);
		resultMap.put("menuName", menuName);
		return resultMap;
	}
	
	/** 
	 * 메뉴 수정 (UPDATE)
	 * @param MenuDTO, Map<String, Object>
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> update(MenuDTO menuDTO, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
		// 해당 메뉴이 있는지 확인
		try {
			menuRepository.findById(menuDTO.getId()).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id: "+menuDTO.getId()));	
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.MENU_DOES_NOT_EXISTS;
			resultMap.put("result", result);
			return resultMap;
		}
		// 메뉴 수정 (UPDATE)
		try {
			menuRepository.save(menuDTO.toEntity());
			resultMap.put("menu", menuDTO);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}
	
	/** 
	 * 메뉴 삭제 (DELETE)
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> param, Map<String, Object> resultMap) {
		ResponseCode result = ResponseCode.SUCCESS;
		// 메뉴 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				menuRepository.deleteById(param.get(p));
			}
			resultMap.put("id", param);
		} catch (Exception e) {
			e.printStackTrace();
			result = ResponseCode.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("result", result);
		return resultMap;
	}
}

