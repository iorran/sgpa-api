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

import com.lgc.ctps.sgpa.domain.Application;
import com.lgc.ctps.sgpa.resource.util.HeaderUtil;
import com.lgc.ctps.sgpa.resource.util.ResponseUtil;
import com.lgc.ctps.sgpa.service.ApplicationService;

@RestController
@RequestMapping("applications")
public class ApplicationResource {
	private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

	private static final String ENTITY_NAME = "application";

	@Autowired
	private ApplicationService applicationService;

	@PostMapping
	public ResponseEntity<Application> createApplication(@Valid @RequestBody Application application) throws URISyntaxException {
		log.debug("REST request to save Application : {}", application);
		if (application.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new application cannot already have an ID"))
					.body(null);
		}

		Application result = applicationService.save(application);
		return ResponseEntity.created(new URI("applications/" + result))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.toString())).body(result);
	}

	@PutMapping
	public ResponseEntity<Application> updateApplication(@Valid @RequestBody Application application) throws URISyntaxException {
		log.debug("REST request to update Application : {}", application);
		if (application.getId() == null) {
			return createApplication(application);
		}
		Application result = applicationService.save(application);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
				.body(result);
	}

	@GetMapping
	public Page<Application> findAllApplication(Application application, Pageable pageable) {
		log.debug("REST request to get a list of application");
		return applicationService.search(application, pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<Application> findOneApplication(@PathVariable Long id) {
		log.debug("REST request to get Application : {}", id);
		Application application = applicationService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(application));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
		log.debug("REST request to delete Application : {}", id);
		applicationService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
				.build();
	}

}
