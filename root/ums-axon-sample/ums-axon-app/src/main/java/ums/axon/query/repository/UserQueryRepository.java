package ums.axon.query.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ums.axon.query.UserEntry;

public interface UserQueryRepository extends MongoRepository<UserEntry, String> {

    /**
     * DOC crazyLau Comment method "findById".
     * 
     * @param id
     * @return
     */
    UserEntry findById(String id);
}
