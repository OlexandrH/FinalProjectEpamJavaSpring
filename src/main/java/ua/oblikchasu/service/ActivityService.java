package ua.oblikchasu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.oblikchasu.repository.ActivityRepository;
import ua.oblikchasu.model.Activity;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService (ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> getAll () {
        return activityRepository.findAll();
    }

    public List<Activity> getByCategory (int catId) {
        return activityRepository.findByCategory(catId);
    }

    public Optional<Activity> getById (int id) {
        return activityRepository.findById(id);
    }

    public Activity add (Activity activity) {
        return activityRepository.save(activity);
    }

    public boolean update (Activity activity) {
        if(!activityRepository.existsById(activity.getId())) {
            return false;
        }
        activityRepository.save(activity);
        return true;
    }

    public boolean delete (Activity activity) {
        if(!activityRepository.existsById(activity.getId())) {
            return false;
        }
        activityRepository.delete(activity);
        return true;
    }

    public boolean deleteById (int id) {
        if(!activityRepository.existsById(id)) {
            return false;
        }
        activityRepository.deleteById(id);
        return true;
    }
}
