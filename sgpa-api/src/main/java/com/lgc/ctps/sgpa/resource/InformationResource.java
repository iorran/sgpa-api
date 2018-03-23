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

import com.lgc.ctps.sgpa.domain.Information;
import com.lgc.ctps.sgpa.resource.util.HeaderUtil;
import com.lgc.ctps.sgpa.resource.util.ResponseUtil;
import com.lgc.ctps.sgpa.service.InformationService;

@RestController
@RequestMapping("informations")
public class InformationResource {
	private final Logger log = LoggerFactory.getLogger(InformationResource.class);

	private static final String ENTITY_NAME = "information";

	@Autowired
	private InformationService informationService;

	@PostMapping
	public ResponseEntity<Information> createInformation(@Valid @RequestBody Information information) throws URISyntaxException {
		log.debug("REST request to save Information : {}", information);
		if (information.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new information cannot already have an ID"))
					.body(null);
		}

		Information result = informationService.save(information);
		return ResponseEntity.created(new URI("informations/" + result))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.toString())).body(result);
	}

	@PutMapping
	public ResponseEntity<Information> updateInformation(@Valid @RequestBody Information information) throws URISyntaxException {
		log.debug("REST request to update Information : {}", information);
		if (information.getId() == null) {
			return createInformation(information);
		}
		Information result = informationService.save(information);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
				.body(result);
	}

	@GetMapping
	public Page<Information> findAllInformation(Pageable pageable) {
		log.debug("REST request to get a list of information");
		return informationService.findAll(pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<Information> findOneInformation(@PathVariable Long id) {
		log.debug("REST request to get Information : {}", id);
		Information information = informationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(information));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteInformation(@PathVariable Long id) {
		log.debug("REST request to delete Information : {}", id);
		informationService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
				.build();
	}

}
