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

import com.lgc.ctps.sgpa.domain.UseCase;
import com.lgc.ctps.sgpa.resource.util.HeaderUtil;
import com.lgc.ctps.sgpa.resource.util.ResponseUtil;
import com.lgc.ctps.sgpa.service.UseCaseService;

@RestController
@RequestMapping("use-cases")
public class UseCaseResource {
	private final Logger log = LoggerFactory.getLogger(UseCaseResource.class);

	private static final String ENTITY_NAME = "useCase";

	@Autowired
	private UseCaseService useCaseService;

	@PostMapping
	public ResponseEntity<UseCase> createUseCase(@Valid @RequestBody UseCase useCase) throws URISyntaxException {
		log.debug("REST request to save UseCase : {}", useCase);
		if (useCase.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new useCase cannot already have an ID"))
					.body(null);
		}

		UseCase result = useCaseService.save(useCase);
		return ResponseEntity.created(new URI("useCases/" + result))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.toString())).body(result);
	}

	@PutMapping
	public ResponseEntity<UseCase> updateUseCase(@Valid @RequestBody UseCase useCase) throws URISyntaxException {
		log.debug("REST request to update UseCase : {}", useCase);
		if (useCase.getId() == null) {
			return createUseCase(useCase);
		}
		UseCase result = useCaseService.save(useCase);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
				.body(result);
	}

	@GetMapping
	public Page<UseCase> findAllUseCase(Pageable pageable) {
		log.debug("REST request to get a list of useCase");
		return useCaseService.findAll(pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<UseCase> findOneUseCase(@PathVariable Long id) {
		log.debug("REST request to get UseCase : {}", id);
		UseCase useCase = useCaseService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(useCase));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteUseCase(@PathVariable Long id) {
		log.debug("REST request to delete UseCase : {}", id);
		useCaseService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
				.build();
	}

}
