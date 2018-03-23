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

import com.lgc.ctps.sgpa.domain.Permission;
import com.lgc.ctps.sgpa.resource.util.HeaderUtil;
import com.lgc.ctps.sgpa.resource.util.ResponseUtil;
import com.lgc.ctps.sgpa.service.PermissionService;

@RestController
@RequestMapping("permissions")
public class PermissionResource {
	private final Logger log = LoggerFactory.getLogger(PermissionResource.class);

	private static final String ENTITY_NAME = "permission";

	@Autowired
	private PermissionService permissionService;

	@PostMapping
	public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission permission) throws URISyntaxException {
		log.debug("REST request to save Permission : {}", permission);
		if (permission.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new permission cannot already have an ID"))
					.body(null);
		}

		Permission result = permissionService.save(permission);
		return ResponseEntity.created(new URI("permissions/" + result))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.toString())).body(result);
	}

	@PutMapping
	public ResponseEntity<Permission> updatePermission(@Valid @RequestBody Permission permission) throws URISyntaxException {
		log.debug("REST request to update Permission : {}", permission);
		if (permission.getId() == null) {
			return createPermission(permission);
		}
		Permission result = permissionService.save(permission);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.toString()))
				.body(result);
	}

	@GetMapping
	public Page<Permission> findAllPermission(Pageable pageable) {
		log.debug("REST request to get a list of permission");
		return permissionService.findAll(pageable);
	}

	@GetMapping("{id}")
	public ResponseEntity<Permission> findOnePermission(@PathVariable Long id) {
		log.debug("REST request to get Permission : {}", id);
		Permission permission = permissionService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(permission));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
		log.debug("REST request to delete Permission : {}", id);
		permissionService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
				.build();
	}

}
