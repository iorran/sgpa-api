package com.lgc.ctps.sgpa.repository.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lgc.ctps.sgpa.domain.Application;

public interface ApplicationRepositoryQuery {
	public Page<Application> search(Application application, Pageable pageable);
}
