package ums.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ums.spring.domain.Role;

public interface RoleQueryRepository extends MongoRepository<Role, String> {
}
