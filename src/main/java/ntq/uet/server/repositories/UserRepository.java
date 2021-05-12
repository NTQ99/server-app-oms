package ntq.uet.server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.auth.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
}