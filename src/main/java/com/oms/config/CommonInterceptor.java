package com.oms.config;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.oms.dto.HistoryDTO;
import com.oms.entity.History;
import com.oms.service.HistoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonInterceptor implements HandlerInterceptor {
	@Autowired
	HistoryService historyService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("**** uri: {}", request.getRequestURI());
		log.info("**** ip: {}", request.getRemoteAddr());
		log.info("**** method: {}", request.getMethod());
		log.info("**** remoteUser: {}", request.getRemoteUser());
		log.info("**** response status: {}", response.getStatus());
		log.info("**** response locale: {}", response.getLocale());
		
		// 모든 요청과 응답이 끝나면 접속로그 DB에 적재
		HistoryDTO historyDTO = new HistoryDTO();
		historyDTO.setRequestUri(request.getRequestURI());
		historyDTO.setMethod(request.getMethod());
		historyDTO.setClientIp(request.getRemoteAddr());		
		if (request.getRemoteUser() == null) {
			historyDTO.setHost("-");
		} else {
			historyDTO.setHost(request.getRemoteUser());
		}
		historyDTO.setStatus(response.getStatus());
		historyDTO.setRequestDate(LocalDateTime.now());
		
		historyService.create(historyDTO);
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		log.info("ServletFilter Init");
//	}
//	
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		log.info("ServletFilter doFilter");
//		// 허용 IP
//		String availableIP[] = {"10.0.100.15", "127.0.0.1"};
//		// 접속한 IP
//		String remoteAddr = request.getRemoteAddr();
////		remoteAddr = "0.0.0.0";
//		
//		// 특정 IP가 아닌 요청이 들어오면 필터에서 차단
//		try {
//			for (int a=0; a<availableIP.length; a++) {
//				if (availableIP[a].equals(remoteAddr)) {
//					log.info("접근 가능한 IP입니다. {}", remoteAddr);
//					chain.doFilter(request, response);
//				}
//			}	
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("접근 불가능한 IP: {}", remoteAddr);
//		}
//		
//	}
//	
//	@Override
//	public void destroy() {
//		log.info("ServletFilter Destroy");
//	}
}
