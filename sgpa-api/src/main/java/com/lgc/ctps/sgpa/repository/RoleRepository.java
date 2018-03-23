package com.lgc.ctps.sgpa.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.lgc.ctps.sgpa.domain.Role;

/**
 * A Role Repository.
 * @author H199653
 */
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    List<Role> findByName(@Param("name") String name);
}
