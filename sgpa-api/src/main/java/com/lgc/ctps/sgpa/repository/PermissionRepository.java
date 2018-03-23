package com.lgc.ctps.sgpa.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.lgc.ctps.sgpa.domain.Permission;

/**
 * A Permission Repository.
 * @author H199653
 */
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
}
