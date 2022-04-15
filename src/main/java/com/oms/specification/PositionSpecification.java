package com.oms.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.oms.entity.Position;

/**
 * Position Specification 클래스
 * @author jsw
 *
 */
public class PositionSpecification {
	/**
	 * WHERE use in (List<Use> use)
	 * @param List<Use> use
	 * @return
	 */
	public static Specification<Position> findByUseYn(Object useYn) {
		return new Specification<Position>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Position> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("useYn").in(useYn);
			}
		};
	}
	
	/**
	 * WHERE name in (List<String> name)
	 * @param List<String>name
	 * @return
	 */
	public static Specification<Position> findByName(Object name) {
		return new Specification<Position>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Position> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("name").in(name);
			}
		};
	} 
}
