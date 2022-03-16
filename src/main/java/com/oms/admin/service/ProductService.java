package com.oms.admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.oms.admin.dto.ProductDTO;
import com.oms.admin.entity.Product;
import com.oms.admin.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	ProductRepository productRepository;
		
	/** 
	 * 상품 목록 조회 (READ)
	 * @return List<Product>
	 */
	public List<ProductDTO> read() {
		// 상품 목록 조회
		List<Product> productList = productRepository.findAll();
		List<ProductDTO> result = productList.stream()											 
											 .map(ProductDTO::new)
											 .collect(Collectors.toList());
		
		log.info("###### productList Result: {}", result);
		
		return result;
	}
	
	/** 
	 * 상품 등록 (CREATE)
	 * @return 등록한 상품 정보
	 */
	@Transactional
	public Product create(@RequestBody ProductDTO productDTO) {
		// 상품 등록 (CREATE)
		Product result = productRepository.save(productDTO.toEntity());

		return result;
	}
	
	/** 
	 * 상품 수정 (UPDATE)
	 * @param @RequestBody
	 * @return 
	 * @return 
	 */
	@Transactional
	public Integer update(@RequestBody ProductDTO productDTO) {
		Long id = productDTO.getId();		
		int result = 0;
		// 해당 상품이 있는지 확인
		productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id: "+id));
		
		try {
			// 상품 수정 (UPDATE)
			productRepository.save(productDTO.toEntity());
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/** 
	 * 상품 삭제 (DELETE)
	 * @return http 응답코드
	 */
	@Transactional
	public Integer delete(List<Long> param) {
		int result = 0;
		
		// 상품 삭제 (DELETE)
		try {
			for (int p=0; p<param.size(); p++) {
				productRepository.deleteById(param.get(p));
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
