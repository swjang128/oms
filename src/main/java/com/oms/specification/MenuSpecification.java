package com.oms.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.oms.entity.Menu;
import com.oms.entity.Menu;

/**
 * Menu Specification
 * @author JSW
 */
public class MenuSpecification {
	/**
	 * WHERE useYn in (Object useYn)
	 * @param List<Use> use
	 * @return
	 */
	public static Specification<Menu> findByUseYn(Object useYn) {
		return new Specification<Menu>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("useYn").in(useYn);
			}
		};
	}
	
	/**
	 * WHERE parentId in (Object parentId)
	 * @param List<String>name
	 * @return
	 */
	public static Specification<Menu> findByParentId(Object parentId) {
		return new Specification<Menu>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("parentId").in(parentId);
			}
		};
	} 
}
