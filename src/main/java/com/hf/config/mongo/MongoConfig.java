package com.hf.config.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.hf.config.GlobalConfig;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages=GlobalConfig.BASEPACKAGES)
public class MongoConfig extends AbstractMongoConfiguration{

	@Override
	protected String getDatabaseName() {
		return "mymgdb";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("172.0.50.1",28885);
	}
	
}
