package com.oms.admin.dto;

import com.oms.admin.entity.Law;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LawDTO {
	private long id;	// ID (Round)
	private int first; // first
	private int second; // second
	private int third; // third
	private int fourth; // fourth
	private int fifth; // fifth
	private int sixth; // sixth
	private int wildCard; // wildCard
	
	/**
	 * (Request) DTO -> Entity
	 * ProductDTO를 Entity로 변환
	 * @return
	 */
	public Law toEntity() {
		return Law.builder()
				.id(id)
				.first(first)
				.second(second)
				.third(third)
				.fourth(fourth)
				.fifth(fifth)
				.sixth(sixth)
				.wildCard(wildCard)
				.build();
	}
	
	public LawDTO(Law law) {
		this.id = law.getId();
		this.first = law.getFirst();
		this.second = law.getSecond();
		this.third = law.getThird();
		this.fourth = law.getFourth();
		this.fifth = law.getFifth();
		this.sixth = law.getSixth();
		this.wildCard = law.getWildCard();
	}
}