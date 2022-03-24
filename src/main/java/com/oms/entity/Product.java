package com.oms.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품
 * @author Capias J
 *
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TB_PRODUCT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 등록번호

	@Column(length=10, nullable=false)
	private String type; // 상품유형 (하드웨어/소프트웨어/기타)

	@Column(length=10, nullable=false)
	private String category; // 상품분류

	@Column(length=128, nullable=false)
	private String name; // 상품명

	@Column(length=16, nullable=false)
	private int price; // 판매가

	@Column(length=10, nullable=false)
	private int cost; // 원가

	@Column(length=2, nullable=false)
	private int rated; // 제한등급

	@Column(length=8, nullable=false)
	private int stock; // 재고

	@Temporal(TemporalType.DATE)
	private Date releaseDate; // 발매일자

	@Column(updatable=false)
	@CreatedDate
	@Temporal(TemporalType.DATE)
	private Date registDate; // 등록일자

	@Column(length=3, nullable=false)
	private double discountRate; // 할인율

	@Builder
	public Product(Long id, String type, String category, String name,
				   int price, int cost, int rated, int stock,
				   Date releaseDate, Date registDate, double discountRate) {
		this.id = id;
		this.type = type;
		this.category = category;
		this.name = name;
		this.price = price;
		this.cost = cost;
		this.rated = rated;
		this.stock = stock;
		this.releaseDate = releaseDate;
		this.registDate = registDate;
		this.discountRate = discountRate;
	}


}
