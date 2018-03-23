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

import com.lgc.ctps.sgpa.domain.Role;
import com.lgc.ctps.sgpa.resource.util.HeaderUtil;
import com.lgc.ctps.sgpa.resource.util.ResponseUtil;
import com.lgc.ctps.sgpa.service.RoleService;

@RestController
@RequestMapping("roles")
public class RoleResource {
	private final Logger log = LoggerFactory.getLogger(RoleResource.class);

	private static final String ENTITY_NAME = "role";

	@Autowired
	private RoleService roleService;

	@PostMapping
	public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) throws URISyntaxException {
		log.debug("REST request to save Role : {}", role);
		if (role.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new role cannot already have an ID"))
					.body(null);
		}

		Role result = roleService.save(role);
		return ResponseEntity.created(new URI("roles/" + result))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.toString())).body(result);
	}

	@PutMapping
	public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role) throws URISyntaxException {
		log.debug("REST request to update Role : {}", role);
		if (role.getId() == null) {
			return createRole(role);
		}
		Role result = roleService.save(role);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
				.body(result);
	}

	@GetMapping
	public Page<Role> findAllRole(Pageable pageable) {
		log.debug("REST request to get a list of role");
		return roleService.findAll(pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<Role> findOneRole(@PathVariable Long id) {
		log.debug("REST request to get Role : {}", id);
		Role role = roleService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(role));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
		log.debug("REST request to delete Role : {}", id);
		roleService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
				.build();
	}

}
