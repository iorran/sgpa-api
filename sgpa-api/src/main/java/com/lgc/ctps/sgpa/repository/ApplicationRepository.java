package com.lgc.ctps.sgpa.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.lgc.ctps.sgpa.domain.Application;
import com.lgc.ctps.sgpa.repository.application.ApplicationRepositoryQuery;

/**
 * A Application Repository.
 * @author H199653
 */
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long>, ApplicationRepositoryQuery {
    List<Application> findByName(@Param("name") String name);
}
