package ntq.uet.server.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ntq.uet.server.models.Tutorial;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {
  List<Tutorial> findByTitleContaining(String title);
  List<Tutorial> findByPublished(boolean published);
}