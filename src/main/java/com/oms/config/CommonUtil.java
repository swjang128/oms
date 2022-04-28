package com.oms.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.oms.service.MenuService;

@Service
public class CommonUtil {
	@Autowired
	MenuService menuService;
	
	/**
	 * ServletPath 가져오기
	 * @param model
	 */
	public String getServletPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getServletPath();
	}
	
	/**
	 * ServletPath의 URL로 현재 서비스 위치를 세팅
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getServiceLocation(Map<String, Object> paramMap, Map<String, Object> resultMap) {		
		return menuService.read(paramMap, resultMap);
	}
}
