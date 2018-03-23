package com.lgc.ctps.sgpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.lgc.ctps.sgpa.domain.PersonRole;

/**
 * A Person Role Repository.
 * @author H199653
 */
public interface PersonRoleRepository extends PagingAndSortingRepository<PersonRole, Long> {
}
