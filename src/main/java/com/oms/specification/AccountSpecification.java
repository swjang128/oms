package com.oms.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.oms.entity.Account;
import com.oms.entity.Account.Role;
import com.oms.entity.Account.Status;
import com.oms.entity.Account.UserStatus;

/**
 * Account Specification 클래스
 * @author jsw
 *
 */
public class AccountSpecification {
	/**
	 * WHERE status in (List<Status> status)
	 * @param status
	 * @return
	 */
	public static Specification<Account> findByStatus(Object status) {
		return new Specification<Account>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("status").in(status);
			}
		};
	}
	
	/**
	 * WHERE user_status in (List<UserStatus> userStatus)
	 * @param userStatus
	 * @return
	 */
	public static Specification<Account> findByUserStatus(Object userStatus) {
		return new Specification<Account>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("userStatus").in(userStatus);
			}
		};
	} 
	
	/**
	 * WHERE role in (List<Role> role)
	 * @param role
	 * @return
	 */
	public static Specification<Account> findByRole(Object role) {
		return new Specification<Account>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("role").in(role);
			}
		};
	} 
	/**
	 * WHERE department in (List<String> department)
	 * @param department
	 * @return
	 */
	public static Specification<Account> findByDepartment(Object department) {
		return new Specification<Account>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("department").in(department);
			}
		};
	} 
	/**
	 * WHERE position in (List<String> position)
	 * @param position
	 * @return
	 */
	public static Specification<Account> findByPosition(Object position) {
		return new Specification<Account>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("position").in(position);
			}
		};
	} 
}
