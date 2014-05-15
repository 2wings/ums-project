package ums.axon.query.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ums.axon.query.RoleEntry;

public interface RoleQueryRepository extends MongoRepository<RoleEntry, String> {
}
