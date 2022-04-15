package com.oms.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.oms.entity.History;

/**
 * History Specification 클래스
 * @author jsw
 *
 */
public class HistorySpecification {
	/**
	 * WHERE host in (List<String> host)
	 * @param List<String> host
	 * @return
	 */
	public static Specification<History> findByHost(Object host) {
		return new Specification<History>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<History> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("host").in(host);
			}
		};
	}
	
	/**
	 * WHERE client_ip in (List<String> clientIp)
	 * @param List<String> clientIp
	 * @return
	 */
	public static Specification<History> findByClientIp(Object clientIp) {
		return new Specification<History>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<History> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("clientIp").in(clientIp);
			}
		};
	}
	
	/**
	 * WHERE request_uri in (List<String> requestUri)
	 * @param List<String> requestUri
	 * @return
	 */
	public static Specification<History> findByRequestUri(Object requestUri) {
		return new Specification<History>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<History> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("requestUri").in(requestUri);
			}
		};
	}
	
	/**
	 * WHERE method in (List<String> method)
	 * @param List<String> method
	 * @return
	 */
	public static Specification<History> findByMethod(Object method) {
		return new Specification<History>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<History> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("method").in(method);
			}
		};
	}
	
	/**
	 * WHERE status in (List<Integer> status)
	 * @param List<Integer> status
	 * @return
	 */
	public static Specification<History> findByStatus(List<Integer> status) {
		return new Specification<History>() {
			private static final long serialVersionUID = -587488762192925433L;
			@Override
			public Predicate toPredicate(Root<History> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return root.get("status").in(status);
			}
		};
	}

}
