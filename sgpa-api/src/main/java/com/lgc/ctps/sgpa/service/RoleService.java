package com.lgc.ctps.sgpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgc.ctps.sgpa.domain.Role;
import com.lgc.ctps.sgpa.repository.RoleRepository;

/**
 * A Role Service.
 * @author H199653
 */
@Service
@Transactional
public class RoleService {

	private final Logger log = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	private RoleRepository roleRepository;

	public Role save(Role role) {
		log.debug("Request to save Role : {}", role);
		return roleRepository.save(role);
	}

	@Transactional(readOnly = true)
	public Page<Role> findAll(Pageable pageable) {
		log.debug("Request to get a list of role");
		return roleRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Role findOne(Long id) {
		log.debug("Request to get Role : {}", id);
		return roleRepository.findOne(id);
	}

	public void delete(Long id) {
		log.debug("Request to delete Role : {}", id);
		roleRepository.delete(id);
	}
}
