package com.oms.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.oms.entity.Menu;

import lombok.extern.slf4j.Slf4j;

/**
 * Menu Specification
 * @author JSW
 */
public class MenuSpecification {
	/**
	 * WHERE parent is null
	 * @param
	 * @return
	 */
	public static Specification<Menu> parentIsNull() {
		return new Specification<Menu>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("parent").isNull();
			}
		};
	}
	
	/**
	 * WHERE useYn in (Object useYn)
	 * @param List<Use> use
	 * @return
	 */
	public static Specification<Menu> findByUseYn(Object useYn) {
		return new Specification<Menu>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("useYn").in(useYn);
			}
		};
	}
	
	/**
	 * WHERE url in (Object url)
	 * @param List<String> url
	 * @return
	 */
	public static Specification<Menu> findByUrl(Object url) {
		return new Specification<Menu>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("url").in(url);
			}
		};
	}
	
	/**
	 * WHERE depth in (Object depth)
	 * @param List<Long> depth
	 * @return
	 */
	public static Specification<Menu> findByDepth(List<Long> depth) {
		return new Specification<Menu>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("depth").in(depth);
			}
		};
	}
}
