package com.lgc.ctps.sgpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EntityScan(basePackageClasses = {SgpaApiApplication.class, Jsr310JpaConverters.class})
@EnableJpaRepositories(basePackageClasses = SgpaApiApplication.class, repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableJpaAuditing
@EnableTransactionManagement
@SpringBootApplication
public class SgpaApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SgpaApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SgpaApiApplication.class);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
	 * Excluimos o bean dataSource do MBeanExporter, pois, ao subir mais de uma
	 * aplicação no mesmo container dava erro.
	 *
	 * Foi necessário adicionar a seguimte linha no application.properties: "spring.datasource.hikari.registerMbeans=true"
	 * @return MBeanExporter without dataSource
	 */
	@Bean
	public MBeanExporter exporter() {
		final MBeanExporter exporter = new MBeanExporter();
		exporter.setAutodetect(true);
		exporter.setExcludedBeans("dataSource");
		return exporter;
	}
}
