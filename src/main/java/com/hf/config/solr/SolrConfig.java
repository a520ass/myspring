package com.hf.config.solr;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;

import com.hf.config.GlobalConfig;

//@Configuration
@EnableSolrRepositories(basePackages=GlobalConfig.BASEPACKAGES)
public class SolrConfig {
	
	@Resource
	private Environment environment;
	
	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient(environment.getProperty("solr.baseURL"));
	}

	@Bean
	public SolrTemplate solrTemplate(SolrClient solrClient) {
		return new SolrTemplate(solrClient);
	}
}
