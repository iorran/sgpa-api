package com.lgc.ctps.sgpa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgc.ctps.sgpa.domain.InformationValue;
import com.lgc.ctps.sgpa.repository.InformationValueRepository;

/**
 * A Information Value Service.
 * @author H199653
 */
@Service
@Transactional
public class InformationValueService {

	private final Logger log = LoggerFactory.getLogger(InformationValueService.class);

	@Autowired
	private InformationValueRepository informationValueRepository;

	public InformationValue save(InformationValue informationValue) {
		log.debug("Request to save Information Value : {}", informationValue);
		return informationValueRepository.save(informationValue);
	}

	@Transactional(readOnly = true)
	public Page<InformationValue> findAll(Pageable pageable) {
		log.debug("Request to get a list of Information Value");
		return informationValueRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public InformationValue findOne(Long id) {
		log.debug("Request to get Information Value : {}", id);
		return informationValueRepository.findOne(id);
	}

	public void delete(Long id) {
		log.debug("Request to delete Information Value : {}", id);
		informationValueRepository.delete(id);
	}
}
