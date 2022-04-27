package com.oms.config;

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
			case "dashboard" -> currentLocation = "대시보드";
			case "account" -> currentLocation = "계정관리";
			case "team" -> currentLocation = "팀관리";
			case "task" -> currentLocation = "업무관리";
			case "project" -> currentLocation = "프로젝트";
			case "product" -> currentLocation = "상품관리";
			case "law" -> currentLocation = "관련법안";
			case "menu" -> currentLocation = "메뉴관리";
		}
		return currentLocation;
	}
}
