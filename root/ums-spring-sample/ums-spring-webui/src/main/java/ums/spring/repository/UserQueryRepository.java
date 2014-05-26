package ums.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ums.spring.domain.User;
import ums.spring.domain.UserEntry;

public interface UserQueryRepository extends MongoRepository<UserEntry, String> {

	UserEntry findById(String userName);
}
