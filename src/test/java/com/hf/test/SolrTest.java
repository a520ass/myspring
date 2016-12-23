package com.hf.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleTermsQuery;
import org.springframework.data.solr.core.query.TermsQuery;
import org.springframework.data.solr.core.query.result.TermsPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hf.config.solr.SolrConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SolrConfig.class)
@TestPropertySource("classpath:solr-test.conf")
public class SolrTest {
	
	
	@Autowired SolrTemplate solrTemplate;
	
	@Test
	public void test() throws Exception{
		try {
			solrTemplate.ping();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

