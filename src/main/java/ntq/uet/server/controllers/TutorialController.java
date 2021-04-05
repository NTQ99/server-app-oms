package ntq.uet.server.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ntq.uet.server.models.BaseResponseModel;
import ntq.uet.server.models.Tutorial;
import ntq.uet.server.services.TutorialService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

  @Autowired
  private TutorialService tutorialService;

  @PostMapping("/tutorials")
  public BaseResponseModel<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    BaseResponseModel<Tutorial> response;
    try {
      response = new BaseResponseModel<>("success", "");
      response.data = tutorialService.createTutorial(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
    } catch (Exception e) {
      response = new BaseResponseModel<>("error", "INTERNAL_SERVER_ERROR");
    }
    return response;
  }

  @GetMapping("/tutorials")
  public BaseResponseModel<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
    BaseResponseModel<List<Tutorial>> response;
    try {
      List<Tutorial> _tutorials = tutorialService.getAllTutorials(title);
      if (_tutorials == null) {
        response = new BaseResponseModel<>("error", "not found");
      } else {
        response = new BaseResponseModel<>("success", "");
        response.data = _tutorials;
      }
    } catch (Exception e) {
      response = new BaseResponseModel<>("error", "INTERNAL_SERVER_ERROR");
    }
    return response;
  }

  @GetMapping("/tutorials/{id}")
  public BaseResponseModel<Tutorial> getTutorialById(@PathVariable("id") String id) {
    BaseResponseModel<Tutorial> response;
    try {
      Tutorial _tutorial = tutorialService.findTutorialById(id);
      if (_tutorial == null) {
        response = new BaseResponseModel<>("error", "not found");
      } else {
        response = new BaseResponseModel<>("success", "");
        response.data = _tutorial;
      }
    } catch (Exception e) {
      response = new BaseResponseModel<>("error", "INTERNAL_SERVER_ERROR");
    }
    return response;
  }

  @GetMapping("/tutorials/published")
  public BaseResponseModel<List<Tutorial>> findByPublished() {
    BaseResponseModel<List<Tutorial>> response;
    try {
      List<Tutorial> _tutorials = tutorialService.findTutorialByPublished(true);
      if (_tutorials == null) {
        response = new BaseResponseModel<>("error", "not found");
      } else {
        response = new BaseResponseModel<>("success", "");
        response.data = _tutorials;
      }
    } catch (Exception e) {
      response = new BaseResponseModel<>("error", "INTERNAL_SERVER_ERROR");
    }
    return response;
  }

  @PutMapping("/tutorials/{id}")
  public BaseResponseModel<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
    BaseResponseModel<Tutorial> response;
    try {
      Tutorial _tutorial = tutorialService.updateTutorial(tutorial, id);
      if (_tutorial == null) {
        response = new BaseResponseModel<>("error", "not found");
      } else {
        response = new BaseResponseModel<>("success", "");
        response.data = _tutorial;
      }
    } catch (Exception e) {
      response = new BaseResponseModel<>("error", "INTERNAL_SERVER_ERROR");
    }
    return response;
  }

  @DeleteMapping("/tutorials/{id}")
  public BaseResponseModel<?> deleteTutorial(@PathVariable("id") String id) {
    BaseResponseModel<?> response;
    try {
      if (tutorialService.deleteTutorial(id)) {
        response = new BaseResponseModel<>("success", "");
      } else {
        response = new BaseResponseModel<>("error", "not found");
      }
    } catch (Exception e) {
      response = new BaseResponseModel<>("error", "INTERNAL_SERVER_ERROR");
    }
    return response;
  }

  @DeleteMapping("/tutorials")
  public BaseResponseModel<?> deleteAllTutorials() {
    BaseResponseModel<?> response;
    try {
      tutorialService.deleteAllTutorialS();
      response = new BaseResponseModel<>("success", "");
    } catch (Exception e) {
      response = new BaseResponseModel<>("error", "INTERNAL_SERVER_ERROR");
    }
    return response;
  }

}