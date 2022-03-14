package com.capias.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CommonUtil {
	/**
	 * ServletPath 가져오기
	 * @param model
	 */
	public String getServletPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		return request.getServletPath();
	}
	
	/**
	 * CurrentLocation 설정해서 가져오기
	 * @param model
	 */
	public String getCurrentLocation() {
		String currentLocation = "";
		
		String servletPath = getServletPath();
		String[] domain = servletPath.split("/");
		
		switch(domain[domain.length-1]) {
			case "member" -> currentLocation = "직원 관리";
			case "product" -> currentLocation = "상품 관리";			
			case "client" -> currentLocation = "고객";
			case "b2c" -> currentLocation = "고객 관리";
			case "b2b" -> currentLocation = "고객사 관리";
			case "law" -> currentLocation = "관련법안";
			case "category" -> currentLocation = "카테고리";
		}
		
		return currentLocation;
	}
}
