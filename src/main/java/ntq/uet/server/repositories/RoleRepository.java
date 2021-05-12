package ntq.uet.server.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.auth.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(Role.ERole name);
}