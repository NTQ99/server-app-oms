package ntq.uet.server.services;

import ntq.uet.server.models.Tutorial;
import ntq.uet.server.repositories.TutorialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("tutorialService")
public class TutorialService {
  @Autowired
  private TutorialRepository tutorialRepository;

  // get by published
  public List<Tutorial> findTutorialByPublished(boolean published) {
    return tutorialRepository.findByPublished(published);
  }

  public Tutorial findTutorialById(String id) {
    return tutorialRepository.findById(id).orElse(null);
  }

  public List<Tutorial> getAllTutorials(String title) {
    if (title == null) {
      return tutorialRepository.findAll();
    } else {
      return tutorialRepository.findByTitleContaining(title);
    }
  }

  public Tutorial createTutorial(Tutorial tutorial) {
    return tutorialRepository.save(tutorial);
  }

  public Tutorial updateTutorial(Tutorial tutorial, String id) {
    Tutorial tutorialData = tutorialRepository.findById(id).orElse(null);
    if (tutorialData != null) {
      tutorialData.setTitle(tutorial.getTitle());
      tutorialData.setDescription(tutorial.getDescription());
      tutorialData.setPublished(tutorial.isPublished());
    } else {
      return null;
    }
    final Tutorial myTutorial = tutorialRepository.save(tutorialData);
    return myTutorial;
  }

  public Boolean deleteTutorial(String id) {
    Tutorial delTutorial = tutorialRepository.findById(id).orElse(null);
    if (delTutorial != null) {
      tutorialRepository.delete(delTutorial);
      return true;
    }
    return false;
  }

  public void deleteAllTutorialS() {
      tutorialRepository.deleteAll();
  }
}