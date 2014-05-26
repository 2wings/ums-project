package ums.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import ums.spring.domain.UserEntry;
import ums.spring.utils.DigestUtils;
import ums.spring.utils.UserFactory;

/**
 * Service for initializing MongoDB with sample data using {@link MongoTemplate}
 */
public class InitMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void init() {
        // Drop existing collections
        mongoTemplate.dropCollection("role");
        mongoTemplate.dropCollection("userEntry");

        // Create new records
        UserEntry liusy = UserFactory.createAdmin("lau", "wings", "liusy", DigestUtils.sha1("aaaaaa"));
        UserEntry fanfree = UserFactory.createAdmin("fan", "zhui", "fanfree", DigestUtils.sha1("bbbbbb"));

        // Insert to db
        mongoTemplate.insert(liusy, "userEntry");
        mongoTemplate.insert(fanfree, "userEntry");
        mongoTemplate.insert(liusy.getRole(), "role");
        mongoTemplate.insert(fanfree.getRole(), "role");
    }
}
