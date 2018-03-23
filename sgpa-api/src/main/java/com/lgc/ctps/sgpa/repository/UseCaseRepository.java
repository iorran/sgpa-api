package com.lgc.ctps.sgpa.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.lgc.ctps.sgpa.domain.UseCase;

/**
 * A Use Case Repository.
 * @author H199653
 */
public interface UseCaseRepository extends PagingAndSortingRepository<UseCase, Long> {
    List<UseCase> findByName(@Param("name") String name);
}
