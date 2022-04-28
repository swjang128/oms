package com.oms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
		int status = ResponseCode.Status.CREATED;
		String message = ResponseCode.Message.CREATED;
		Menu menu = null;
		// 메뉴 등록 (CREATE)
		try {
			menu = menuRepository.save(menuDTO.toEntity());
			resultMap.put("menu", menu);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
	
	/** 
	 * 메뉴 목록 조회 (READ)
	 * @param Map<String, Object>, Map<String, Object>
	 * @return Map<String, Object>
	 */
	public Map<String, Object> read(Map<String, Object> paramMap, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		List<Menu> menu = new ArrayList<Menu>();
		List<MenuDTO> menuDTO = new ArrayList<MenuDTO>();
		Object useYn = paramMap.get("useYn");
		Object parentId = paramMap.get("parentId");
		Object url = paramMap.get("url");
		Specification<Menu> specification = (root, query, criteriaBuilder) -> null;
		// Specification 설정
		if (useYn != null)
			specification = specification.and(MenuSpecification.findByUseYn(useYn));
		if (parentId != null)
			specification = specification.and(MenuSpecification.findByParentId(parentId));
		if (url != null) 
			specification = specification.and(MenuSpecification.findByUrl(url));
		// 메뉴 목록 조회
		try {
			menu = menuRepository.findAll(specification);
			menuDTO = menu.stream().map(MenuDTO::new).collect(Collectors.toList());			
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		resultMap.put("menuList", menuDTO);
		return resultMap;
	}
	
	/** 
	 * 메뉴 수정 (UPDATE)
	 * @param MenuDTO, Map<String, Object>
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> update(MenuDTO menuDTO, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;		
		// 해당 메뉴이 있는지 확인
		try {
			menuRepository.findById(menuDTO.getId()).orElseThrow(() -> new IllegalArgumentException("해당 메뉴이 없습니다. id: "+menuDTO.getId()));	
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.MENU_NOT_FOUND;
			message = ResponseCode.Message.MENU_NOT_FOUND;
			resultMap.put("status", status);
			resultMap.put("message", message);
			return resultMap;
		}
		// 메뉴 수정 (UPDATE)
		try {
			menuRepository.save(menuDTO.toEntity());
			resultMap.put("menu", menuDTO);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
	
	/** 
	 * 메뉴 삭제 (DELETE)
	 * @return Map<String, Object>
	 */
	@Transactional
	public Map<String, Object> delete(List<Long> param, Map<String, Object> resultMap) {
		int status = ResponseCode.Status.OK;
		String message = ResponseCode.Message.OK;
		// 메뉴 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				menuRepository.deleteById(param.get(p));
			}
			resultMap.put("id", param);
		} catch (Exception e) {
			e.printStackTrace();
			status = ResponseCode.Status.ERROR_ABORT;
			message = ResponseCode.Message.ERROR_ABORT;
		}
		// 결과 리턴
		resultMap.put("status", status);
		resultMap.put("message", message);
		return resultMap;
	}
}

