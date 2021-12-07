package ua.oblikchasu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.oblikchasu.repository.UsersActivityRepository;
import ua.oblikchasu.model.UsersActivity;

import java.util.List;
import java.util.Optional;

@Service
public class UsersActivityService {

    private final UsersActivityRepository usersActivityRepository;

    @Autowired
    public UsersActivityService (UsersActivityRepository usersActivityRepository) {
        this.usersActivityRepository = usersActivityRepository;
    }

    public List<UsersActivity> getAll () {
        return usersActivityRepository.findAll();
    }

    public List<UsersActivity> getForUser (int userId) {
        return usersActivityRepository.findForUser(userId);
    }

    public Optional<UsersActivity> getById (int id) {
        return usersActivityRepository.findById(id);
    }

    public UsersActivity add(UsersActivity usersActivity) {
        return usersActivityRepository.save(usersActivity);
    }

    public boolean update (UsersActivity usersActivity) {
        if(usersActivityRepository.existsById(usersActivity.getId())) {
            usersActivityRepository.save(usersActivity);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete (UsersActivity usersActivity) {
        if(usersActivityRepository.existsById(usersActivity.getId())) {
            usersActivityRepository.delete(usersActivity);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteById (int id) {
        if(usersActivityRepository.existsById(id)) {
            usersActivityRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Page<UsersActivity> getPaginated (int userId, int pageNo, int pageSize, String sortBy, String sortOrder) {

        int totalCount = usersActivityRepository.countForUser(userId);
        int totalPages = totalCount/pageSize;
        if(totalCount%pageSize > 0) {
            totalPages++;
        }

        int from = (pageNo - 1) * pageSize;
        List<UsersActivity> usersActivityList = null;
        if (sortBy.equals("id")) {
            if(sortOrder.equals("asc")) {
                usersActivityList = usersActivityRepository.findForUserSortedByIdAsc(userId, from, pageSize);
            } else {
                usersActivityList = usersActivityRepository.findForUserSortedByIdDesc(userId, from, pageSize);
            }
        } else if (sortBy.equals("category")) {
            if(sortOrder.equals("asc")) {
                usersActivityList = usersActivityRepository.findForUserSortedByCategoryAsc(userId, from, pageSize);
            } else {
                usersActivityList = usersActivityRepository.findForUserSortedByCategoryDesc(userId, from, pageSize);
            }
        } else if (sortBy.equals("activity")) {
            if(sortOrder.equals("asc")) {
                usersActivityList = usersActivityRepository.findForUserSortedByActivityAsc(userId, from, pageSize);
            } else {
                usersActivityList = usersActivityRepository.findForUserSortedByActivityDesc(userId, from, pageSize);
            }
        } else if (sortBy.equals("time")) {
            if(sortOrder.equals("asc")) {
                usersActivityList = usersActivityRepository.findForUserSortedByTimeAsc(userId, from, pageSize);
            } else {
                usersActivityList = usersActivityRepository.findForUserSortedByTimeDesc(userId, from, pageSize);
            }
        } else if (sortBy.equals("status")) {
            if(sortOrder.equals("asc")) {
                usersActivityList = usersActivityRepository.findForUserSortedByStatusAsc(userId, from, pageSize);
            } else {
                usersActivityList = usersActivityRepository.findForUserSortedByStatusDesc(userId, from, pageSize);
            }
        }
        return new GenericPage<>(totalPages, usersActivityList);

    }
}
