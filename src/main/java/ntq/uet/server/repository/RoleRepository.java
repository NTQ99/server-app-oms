package ntq.uet.server.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.model.entity.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(Role.ERole name);
}