package com.lgc.ctps.sgpa.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.lgc.ctps.sgpa.domain.InformationValue;

/**
 * A Information Value Repository.
 * @author H199653
 */
public interface InformationValueRepository extends PagingAndSortingRepository<InformationValue, Long> {
    List<InformationValue> findByValue(@Param("value") String value);
}
