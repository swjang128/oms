package com.oms.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.oms.entity.Department;

/**
 * Department Specification 클래스
 * @author jsw
 *
 */
public class DepartmentSpecification {
	/**
	 * WHERE use in (List<Use> use)
	 * @param List<Use> use
	 * @return
	 */
	public static Specification<Department> findByUseYn(Object useYn) {
		return new Specification<Department>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("useYn").in(useYn);
			}
		};
	}
	
	/**
	 * WHERE name in (List<String> name)
	 * @param List<String>name
	 * @return
	 */
	public static Specification<Department> findByName(Object name) {
		return new Specification<Department>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("name").in(name);
			}
		};
	} 
}
