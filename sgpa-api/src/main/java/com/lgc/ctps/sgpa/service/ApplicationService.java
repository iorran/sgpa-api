package com.lgc.ctps.sgpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgc.ctps.sgpa.domain.Application;
import com.lgc.ctps.sgpa.repository.ApplicationRepository;

/**
 * A Application Service.
 * @author H199653
 */
@Service
@Transactional
public class ApplicationService {

	private final Logger log = LoggerFactory.getLogger(ApplicationService.class);

	@Autowired
	private ApplicationRepository applicationRepository;

	public Application save(Application application) {
		log.debug("Request to save Application : {}", application);
		return applicationRepository.save(application);
	}

	@Transactional(readOnly = true)
	public Page<Application> search(Application application, Pageable pageable) {
		log.debug("Request to get a list of application");
//		return applicationRepository.findAll(pageable);
		return applicationRepository.search(application, pageable);
	}

	@Transactional(readOnly = true)
	public Application findOne(Long id) {
		log.debug("Request to get Application : {}", id);
		return applicationRepository.findOne(id);
	}

	public void delete(Long id) {
		log.debug("Request to delete Application : {}", id);
		applicationRepository.delete(id);
	}
}
