package com.lgc.ctps.sgpa.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lgc.ctps.sgpa.domain.InformationValue;
import com.lgc.ctps.sgpa.resource.util.HeaderUtil;
import com.lgc.ctps.sgpa.resource.util.ResponseUtil;
import com.lgc.ctps.sgpa.service.InformationValueService;

@RestController
@RequestMapping("information-values")
public class InformationValueResource {
	private final Logger log = LoggerFactory.getLogger(InformationValueResource.class);

	private static final String ENTITY_NAME = "informationValue";

	@Autowired
	private InformationValueService informationValueService;

	@PostMapping
	public ResponseEntity<InformationValue> createInformationValue(@Valid @RequestBody InformationValue informationValue) throws URISyntaxException {
		log.debug("REST request to save InformationValue : {}", informationValue);
		if (informationValue.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new informationValue cannot already have an ID"))
					.body(null);
		}

		InformationValue result = informationValueService.save(informationValue);
		return ResponseEntity.created(new URI("informationValues/" + result))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.toString())).body(result);
	}

	@PutMapping
	public ResponseEntity<InformationValue> updateInformationValue(@Valid @RequestBody InformationValue informationValue) throws URISyntaxException {
		log.debug("REST request to update InformationValue : {}", informationValue);
		if (informationValue.getId() == null) {
			return createInformationValue(informationValue);
		}
		InformationValue result = informationValueService.save(informationValue);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
				.body(result);
	}

	@GetMapping
	public Page<InformationValue> findAllInformationValue(Pageable pageable) {
		log.debug("REST request to get a list of informationValue");
		return informationValueService.findAll(pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<InformationValue> findOneInformationValue(@PathVariable Long id) {
		log.debug("REST request to get InformationValue : {}", id);
		InformationValue informationValue = informationValueService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(informationValue));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteInformationValue(@PathVariable Long id) {
		log.debug("REST request to delete InformationValue : {}", id);
		informationValueService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
				.build();
	}

}
