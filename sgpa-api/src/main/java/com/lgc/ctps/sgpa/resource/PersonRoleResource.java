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

import com.lgc.ctps.sgpa.domain.PersonRole;
import com.lgc.ctps.sgpa.resource.util.HeaderUtil;
import com.lgc.ctps.sgpa.resource.util.ResponseUtil;
import com.lgc.ctps.sgpa.service.PersonRoleService;

@RestController
@RequestMapping("person-roles")
public class PersonRoleResource {
	private final Logger log = LoggerFactory.getLogger(PersonRoleResource.class);

	private static final String ENTITY_NAME = "personRole";

	@Autowired
	private PersonRoleService personRoleService;

	@PostMapping
	public ResponseEntity<PersonRole> createPersonRole(@Valid @RequestBody PersonRole personRole) throws URISyntaxException {
		log.debug("REST request to save PersonRole : {}", personRole);
		if (personRole.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new personRole cannot already have an ID"))
					.body(null);
		}
		PersonRole result = personRoleService.save(personRole);
		return ResponseEntity.created(new URI("person-roles/" + result))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.toString())).body(result);
	}

	@PutMapping
	public ResponseEntity<PersonRole> updatePersonRole(@Valid @RequestBody PersonRole personRole) throws URISyntaxException {
		log.debug("REST request to update PersonRole : {}", personRole);
		if (personRole.getId() == null) {
			return createPersonRole(personRole);
		}
		PersonRole result = personRoleService.save(personRole);
		return ResponseEntity.created(new URI("person-roles/" + result))
			.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.toString())).body(result);
	}

	@GetMapping
	public Page<PersonRole> findAllPersonRole(Pageable pageable) {
		log.debug("REST request to get a list of personRole");
		return personRoleService.findAll(pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<PersonRole> findOnePersonRole(@PathVariable Long id) {
		log.debug("REST request to get PersonRole : {}", id);
		PersonRole personRole = personRoleService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personRole));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletePersonRole(@PathVariable Long id) {
		log.debug("REST request to delete PersonRole : {}", id);
		personRoleService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
				.build();
	}

}
