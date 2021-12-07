package ua.oblikchasu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Activity> getPaginated (int pageNo, int pageSize, String sortBy, String sortOrder) {
        long totalCount = activityRepository.count();
        int totalPages = (int)(totalCount/pageSize);
        if(totalCount%pageSize > 0) {
            totalPages++;
        }
        List<Activity> activityList;
        if(sortBy.equals("category")) {
            if (sortOrder.equals("asc")) {
                activityList = activityRepository.findSortedByCategoryNameAsc((pageNo - 1) * pageSize, pageSize);
            } else {
                activityList = activityRepository.findSortedByCategoryNameDesc((pageNo - 1) * pageSize, pageSize);
            }
            return new GenericPage<>(totalPages,activityList);
        } else if (sortBy.equals("totalTime")) {
            if (sortOrder.equals("asc")) {
                activityList = activityRepository.findSortedByTotalTimeAsc((pageNo - 1) * pageSize, pageSize);
            } else {
                activityList = activityRepository.findSortedByTotalTimeDesc((pageNo - 1) * pageSize, pageSize);
            }
            return new GenericPage<>(totalPages,activityList);
        } else if (sortBy.equals("userCount")) {
            if (sortOrder.equals("asc")) {
                activityList = activityRepository.findSortedByUserCountAsc((pageNo - 1) * pageSize, pageSize);
            } else {
                activityList = activityRepository.findSortedByUserCountDesc((pageNo - 1) * pageSize, pageSize);
            }
            return new GenericPage<>(totalPages,activityList);
        }

            Sort sort = Sort.by(sortBy).ascending();

            if (sortOrder.equalsIgnoreCase(Sort.Direction.DESC.name())) {
                sort = Sort.by(sortBy).descending();
            }

            Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
            return activityRepository.findAll(pageable);
    }

    public int getTotalTime (int activityId) {
        Optional<Integer> optTotalTime = activityRepository.findTotalTimeByActivity(activityId);
        return optTotalTime.orElse(0);
    }

    public int getUserCount (int activityId) {
        Optional<Integer> optUserCount = activityRepository.findUserCountByActivity(activityId);
        return optUserCount.orElse(0);
    }
}
