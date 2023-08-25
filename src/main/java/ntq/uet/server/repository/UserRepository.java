package ntq.uet.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.model.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
}