package com.oms.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.oms.dto.HistoryDTO;
import com.oms.service.HistoryService;
import com.oms.service.MenuService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonInterceptor implements HandlerInterceptor {
	@Autowired
	HistoryService historyService;
	@Autowired
	MenuService menuService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// url, menuName은 기본 공백으로 지정
		request.setAttribute("url", "");
		request.setAttribute("menuName", "");
		
		// 요청한 servletPath가 API가 아닌 Web 요청이면 menuName과 url 설정
		String servletPath[] = request.getServletPath().split("/");
		
		if ("oms".equals(servletPath[1])) {
			log.info("================ Web 요청");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			paramMap.put("url", servletPath[servletPath.length-1]);
			resultMap = menuService.readServiceLocation(paramMap, resultMap);
			request.setAttribute("url", resultMap.get("url"));
			request.setAttribute("menuName", resultMap.get("menuName"));
			log.info("##### url: {}", request.getAttribute("url"));
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 모든 요청과 응답이 끝나면 접속로그 DB에 적재
		HistoryDTO historyDTO = new HistoryDTO();
		historyDTO.setRequestUri(request.getRequestURI());
		historyDTO.setMethod(request.getMethod());
		historyDTO.setClientIp(request.getRemoteAddr());		
		if (request.getRemoteUser() == null) {
			historyDTO.setHost("");
		} else {
			historyDTO.setHost(request.getRemoteUser());
		}
		historyDTO.setStatus(response.getStatus());
		historyDTO.setRequestTime(LocalDateTime.now());
		historyService.create(historyDTO);
		// 인터셉터 afterCompletion 처리 완료
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
