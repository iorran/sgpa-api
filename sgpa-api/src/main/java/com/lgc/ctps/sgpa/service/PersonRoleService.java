package com.lgc.ctps.sgpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgc.ctps.sgpa.domain.PersonRole;
import com.lgc.ctps.sgpa.repository.PersonRoleRepository;

/**
 * A PersonRole Service.
 * @author H199653
 */
@Service
@Transactional
public class PersonRoleService {

	private final Logger log = LoggerFactory.getLogger(PersonRoleService.class);

	@Autowired
	private PersonRoleRepository personRoleRepository;

	public PersonRole save(PersonRole personRole) {
		log.debug("Request to save PersonRole : {}", personRole);
		return personRoleRepository.save(personRole);
	}

	@Transactional(readOnly = true)
	public Page<PersonRole> findAll(Pageable pageable) {
		log.debug("Request to get a list of personRole");
		return personRoleRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public PersonRole findOne(Long id) {
		log.debug("Request to get PersonRole : {}", id);
		return personRoleRepository.findOne(id);
	}

	public void delete(Long id) {
		log.debug("Request to delete PersonRole : {}", id);
		personRoleRepository.delete(id);
	}
}
