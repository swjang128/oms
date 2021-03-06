package com.oms.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oms.config.ResponseManager;
import com.oms.dto.ProductDTO;
import com.oms.entity.Product;
import com.oms.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * Product 서비스에 대한 Rest API
 * @author capias J
 *
 */
@RestController
@Slf4j
@RequestMapping("api")
public class ProductAPI {
	@Autowired
	ProductService productService;
//	
//	/**
//	 * 상품 목록 조회
//	 * @param model
//	 * @param request
//	 * @return 
//	 */
//	@GetMapping("product")
//	public Map<String, Object> read() {
//		// 기본 변수 설정
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<ProductDTO> productList = new ArrayList<ProductDTO>();
//		int status = ResponseManager.Status.ERROR_ABORT;
//		String message = ResponseManager.Message.ERROR_ABORT;
//		
//		// 상품 목록 조회
//		try {
//			productList = productService.read();
//			status = ResponseManager.Status.OK;
//			message = ResponseManager.Message.OK;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		// RESTful API 결과를 리턴
//		resultMap.put("productList", productList);
//		resultMap.put("message", message);
//		resultMap.put("status", status);
//		
//		return resultMap;
//	}
//	
//	/**
//	 * 상품 등록 (CREATE)
//	 * @param RequestBody
//	 * @throws IOException 
//	 */
//	@PostMapping("product")
//	public Map<String, Object> create(@Valid @RequestBody ProductDTO productDTO) {
//		// 기본 변수 설정
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		int status = ResponseManager.Status.ERROR_ABORT;		
//		String message = ResponseManager.Message.ERROR_ABORT;
//		Product result = null;
//		
//		// 상품 등록
//		result = productService.create(productDTO);
//		if (result != null) {
//			status = ResponseManager.Status.CREATED;
//			message = ResponseManager.Message.CREATED;	
//		}
//		
//		// RESTful API 결과를 리턴
//		resultMap.put("result",  result);
//		resultMap.put("status",  status);
//		resultMap.put("message",  message);
//		
//		return resultMap;
//	}
//	
//	/**
//	 * 상품 삭제 (DELETE)
//	 * @param PathVariable
//	 * @return 
//	 */
//	@DeleteMapping("product/{id}")
//	public Map<String, Object> delete(@PathVariable("id") List<Long> param) {
//		log.info("**** delete called");
//		// 기본 변수 설정
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		int result = 0;
//		int status = ResponseManager.Status.NOT_FOUND;		
//		String message = ResponseManager.Message.NOT_FOUND;
//		
//		// 상품 삭제
//		try {
//			result = productService.delete(param);
//			if (result == 1) {
//				status = ResponseManager.Status.OK;
//				message = ResponseManager.Message.OK;	
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		// RESTful API 결과를 리턴
//		resultMap.put("status",  status);
//		resultMap.put("message",  message);
//		
//		return resultMap;
//	}
//	
//	/**
//	 * 상품 수정 (UPDATE)
//	 * @param RequestBody
//	 * @return 
//	 */
//	@PutMapping("product")
//	public Map<String, Object> update(@Valid @RequestBody ProductDTO productDTO) {
//		// 기본 변수 설정
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		int result = 0;
//		int status = ResponseManager.Status.ERROR_ABORT;		
//		String message = ResponseManager.Message.ERROR_ABORT;
////		Long id = payload.getId();
//		
//		// 상품 수정
//		try {
//			result = productService.update(productDTO);
//			if (result == 1) {
//				status = ResponseManager.Status.OK;
//				message = ResponseManager.Message.OK;
//			}	
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		// RESTful API 결과를 리턴
//		resultMap.put("status",  status);
//		resultMap.put("message",  message);
////		resultMap.put("id", id);
//		
//		return resultMap;
//	}

}
