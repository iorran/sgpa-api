package com.lgc.ctps.sgpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgc.ctps.sgpa.domain.UseCase;
import com.lgc.ctps.sgpa.repository.UseCaseRepository;

/**
 * A Use Case Service.
 * @author H199653
 */
@Service
@Transactional
public class UseCaseService {

	private final Logger log = LoggerFactory.getLogger(UseCaseService.class);

	@Autowired
	private UseCaseRepository useCaseRepository;

	public UseCase save(UseCase useCase) {
		log.debug("Request to save Use Case : {}", useCase);
		return useCaseRepository.save(useCase);
	}

	@Transactional(readOnly = true)
	public Page<UseCase> findAll(Pageable pageable) {
		log.debug("Request to get a list of useCase");
		return useCaseRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public UseCase findOne(Long id) {
		log.debug("Request to get Use Case : {}", id);
		return useCaseRepository.findOne(id);
	}

	public void delete(Long id) {
		log.debug("Request to delete Use Case : {}", id);
		useCaseRepository.delete(id);
	}
}
