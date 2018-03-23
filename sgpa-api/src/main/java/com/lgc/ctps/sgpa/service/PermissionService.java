package com.lgc.ctps.sgpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgc.ctps.sgpa.domain.Permission;
import com.lgc.ctps.sgpa.repository.PermissionRepository;

/**
 * A Permission Service.
 * @author H199653
 */
@Service
@Transactional
public class PermissionService {

	private final Logger log = LoggerFactory.getLogger(PermissionService.class);

	@Autowired
	private PermissionRepository permissionRepository;

	public Permission save(Permission permission) {
		log.debug("Request to save Permission : {}", permission);
		return permissionRepository.save(permission);
	}

	@Transactional(readOnly = true)
	public Page<Permission> findAll(Pageable pageable) {
		log.debug("Request to get a list of permission");
		return permissionRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Permission findOne(Long id) {
		log.debug("Request to get Permission : {}", id);
		return permissionRepository.findOne(id);
	}

	public void delete(Long id) {
		log.debug("Request to delete Permission : {}", id);
		permissionRepository.delete(id);
	}
}
