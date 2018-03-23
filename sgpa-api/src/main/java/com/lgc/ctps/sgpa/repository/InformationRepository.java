package com.lgc.ctps.sgpa.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.lgc.ctps.sgpa.domain.Information;

/**
 * A Information Repository.
 * @author H199653
 */
public interface InformationRepository extends PagingAndSortingRepository<Information, Long> {
    List<Information> findByName(@Param("name") String name);
}
