package com.capias.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 법률
 * @author Capias J
 *
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TB_LAW")
public class Law {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;							// ID (Round)

	@Column(length=2, nullable=false)
	private int first;							// First

	@Column(length=2, nullable=false)
	private int second;							// Second

	@Column(length=2, nullable=false)
	private int third;							// Third

	@Column(length=2, nullable=false)
	private int fourth;							// Fourth

	@Column(length=2, nullable=false)
	private int fifth;							// Fifth

	@Column(length=2, nullable=false)
	private int sixth;							// Sixth

	@Column(length=2, nullable=false)
	private int wildCard;						// WildCard


	@Builder
	public Law(Long id, int first, int second, int third,
			   int fourth, int fifth, int sixth, int wildCard) {
		this.id = id;
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
		this.sixth = sixth;
		this.wildCard = wildCard;
	}

}
