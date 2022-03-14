package com.capias.admin.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.capias.admin.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	private long id;	// 등록번호 (기본키)
	
	@NotBlank(message="상품유형을 입력해주세요.")
	@Size(min=1, max=10, message="상품유형 값이 잘못되었습니다.")
	private String type; // 상품유형 (하드웨어/소프트웨어/기타)
	
	@NotBlank(message="상품분류를 입력해주세요.")
	@Size(min=1, max=10, message="상품분류 값이 잘못되었습니다.")
	private String category; // 상품분류
	
	@NotBlank(message="상품명을 입력해주세요.")
	@Size(min=1, max=128, message="상품명 값이 잘못되었습니다.")
	private String name; // 상품명
	
	@PositiveOrZero(message="판매가 값이 잘못되었습니다.")
	private int price; // 판매가
	
	@PositiveOrZero(message="원가 값이 잘못되었습니다.")
	private int cost; // 원가
	
	@PositiveOrZero(message="제한등급 값이 잘못되었습니다.")
	private int rated; // 제한등급
	
	@PositiveOrZero(message="재고 값이 잘못되었습니다.")
	private int stock; // 재고
	
	private Date releaseDate; // 발매일자
	private Date registDate; // 등록일자
	
	private double discountRate; // 할인율
	
	private int afterDiscountPrice; // 할인 후 금액 (price * (1 - (discountRate / 100))
	
	/**
	 * (Request) DTO -> Entity
	 * ProductDTO를 Entity로 변환
	 * @return
	 */
	public Product toEntity() {
		Date date = new Date();
		return Product.builder()
				.id(id)
				.type(type)
				.category(category)
				.name(name)
				.price(price)
				.cost(cost)
				.rated(rated)
				.stock(stock)
				.registDate(date)
				.releaseDate(releaseDate)
				.discountRate(discountRate)
				.build();
	}
	
	public ProductDTO(Product product) {
		this.id = product.getId();
		this.type = product.getType();
		this.category = product.getCategory();
		this.name = product.getName();
		this.price = product.getPrice();
		this.cost = product.getCost();
		this.rated = product.getRated();
		this.stock = product.getStock();
		this.registDate = product.getRegistDate();
		this.releaseDate = product.getReleaseDate();
		this.discountRate = product.getDiscountRate();
		this.afterDiscountPrice = (int) (product.getPrice() * (1 - (product.getDiscountRate() / 100)));		// 할인 후 금액 (price * (1 - (discountRate / 100))
	}
}