package com.lgc.ctps.sgpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgc.ctps.sgpa.domain.Information;
import com.lgc.ctps.sgpa.repository.InformationRepository;

/**
 * A Information Service.
 * @author H199653
 */
@Service
@Transactional
public class InformationService {

	private final Logger log = LoggerFactory.getLogger(InformationService.class);

	@Autowired
	private InformationRepository informationRepository;

	public Information save(Information information) {
		log.debug("Request to save Information : {}", information);
		return informationRepository.save(information);
	}

	@Transactional(readOnly = true)
	public Page<Information> findAll(Pageable pageable) {
		log.debug("Request to get a list of information");
		return informationRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Information findOne(Long id) {
		log.debug("Request to get Information : {}", id);
		return informationRepository.findOne(id);
	}

	public void delete(Long id) {
		log.debug("Request to delete Information : {}", id);
		informationRepository.delete(id);
	}
}
