package com.lgc.ctps.sgpa.repository.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.lgc.ctps.sgpa.domain.Application;
import com.lgc.ctps.sgpa.domain.Application_;

public class ApplicationRepositoryImpl implements ApplicationRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Application> search(Application filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Application> criteria = builder.createQuery(Application.class);
		Root<Application> application = criteria.from(Application.class);

		Predicate[] predicates = restrictionsBuilder(filter, builder, application);
		criteria.where(predicates);

		List<Order> orderList = orderListBuilder(builder, pageable, application);
		criteria.orderBy(orderList);

		TypedQuery<Application> query = manager.createQuery(criteria);

		addPaginate(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	private List<Order> orderListBuilder(CriteriaBuilder builder, Pageable pageable, Root<Application> application) {
		List<Order> orderList = new ArrayList<>();

		if (ObjectUtils.isEmpty(pageable.getSort())) {
			orderList.add(builder.desc(application.get(Application_.id)));
			return orderList;
		}

		for (Sort.Order order : pageable.getSort()) {
			if (order.getProperty().equalsIgnoreCase("name")) {
				orderList.add(order.getDirection().isAscending()
						? builder.asc(builder.upper(application.get(Application_.name)))
						: builder.desc(builder.upper(application.get(Application_.name))));
			}
		}

		return orderList;
	}

	private Predicate[] restrictionsBuilder(Application filter, CriteriaBuilder builder,
			Root<Application> application) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(filter.getId())) {
			predicates	.add(builder.equal(application.get(Application_.id), filter.getId()));
		}

		if (!StringUtils.isEmpty(filter.getName())) {
			predicates.add(builder.like(builder.lower(application.get(Application_.name)),
					"%" + filter.getName().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addPaginate(TypedQuery<Application> query, Pageable pageable) {
		int page = pageable.getPageNumber();
		int maxResult = pageable.getPageSize();
		int startPosition = page * maxResult;

		query.setFirstResult(startPosition);
		query.setMaxResults(maxResult);
	}

	private Long total(Application filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Application> application = criteria.from(Application.class);
		criteria.select(builder.count(application));

		Predicate[] predicates = restrictionsBuilder(filter, builder, application);
		criteria.where(predicates);

		return manager.createQuery(criteria).getSingleResult();
	}
}
