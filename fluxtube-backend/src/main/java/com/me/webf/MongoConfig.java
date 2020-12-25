package com.me.webf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.session.data.mongo.config.annotation.web.reactive.EnableMongoWebSession;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;


@Configuration
@EnableReactiveMongoRepositories
@EnableTransactionManagement
@EnableMongoWebSession
public class MongoConfig extends AbstractReactiveMongoConfiguration {
	
	@Value("${spring.data.mongodb.uri}")
    private String mongoUri;
	
	@Value("${spring.data.mongodb.database}")
    private String mongoName;
	
	
	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate(ReactiveMongoDatabaseFactory reactiveMongoDbFactory,
			MappingMongoConverter mappingMongoConverter) {
		return new ReactiveMongoTemplate(reactiveMongoDbFactory, mappingMongoConverter);
	}

	@Bean
	public ReactiveGridFsTemplate reactiveGridFsTemplate(ReactiveMongoDatabaseFactory reactiveMongoDbFactory,
			MappingMongoConverter mappingMongoConverter) {
		return new ReactiveGridFsTemplate(reactiveMongoDbFactory, mappingMongoConverter);
	}

	@Override
	protected String getDatabaseName() {
		return mongoName;

	}

	@Override
	public MongoClient reactiveMongoClient() {
		return MongoClients.create(mongoUri);
	}

	@Bean
	public ReactiveMongoDatabaseFactory reactiveMongoDbFactory(MongoClient reactiveMongoClient) {
		return new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient, getDatabaseName());
	}
	
	@Bean
    public TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
        return TransactionalOperator.create(transactionManager);
    }

    @Bean
    public ReactiveTransactionManager transactionManager(ReactiveMongoDatabaseFactory reactiveMongoDbFactory) {
        return new ReactiveMongoTransactionManager(reactiveMongoDbFactory);
    }

}