package ums.axon.infra;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.axonframework.saga.repository.mongo.MongoTemplate;
import org.springframework.data.mongodb.MongoDbFactory;

/**
 * @author liushuangyi@126.com
 */
public class DefaultSagaMongoTemplate implements MongoTemplate {

    static final String SAGA_COLLECTION = "sagacollection";
    private MongoDbFactory mongoDbFactory;

    public DefaultSagaMongoTemplate(MongoDbFactory mongoDbFactory) {
        this.mongoDbFactory = mongoDbFactory;
    }

    @Override
    public DBCollection sagaCollection() {
        return database().getCollection(SAGA_COLLECTION);
    }

    private DB database() {
        return mongoDbFactory.getDb();
    }
}
