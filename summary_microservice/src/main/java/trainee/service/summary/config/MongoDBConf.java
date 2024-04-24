//package trainee.service.summary.config;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//import javax.validation.constraints.NotNull;
//
//@Configuration
//@EnableMongoRepositories(basePackages = "trainee.service.summary.dao")
//public class MongoDBConf extends AbstractMongoClientConfiguration {
//
//    @Value("${spring.data.mongodb.host}")
//    private String host;
//
//    @Value("${spring.data.mongodb.port}")
//    private int port;
//
//    @Value("${spring.data.mongodb.database}")
//    private String database;
//
////    @Override
////    protected String getDatabaseName() {
////        return database;
////    }
////
////    @Override
////    public MongoClient mongoClient() {
////        return MongoClients.create(String.format("mongodb://%s:%d", host, port));
////    }
//
//    @Bean
//    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
//        return new MongoTemplate(mongoClient, database);
//    }
//
////    @Bean
////    public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean factory) {
////        return new ValidatingMongoEventListener(factory);
////    }
////
////    @Override
////    protected boolean autoIndexCreation() {
////        return true;
////    }
//}
