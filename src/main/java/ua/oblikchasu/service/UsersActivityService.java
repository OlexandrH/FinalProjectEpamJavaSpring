package ua.oblikchasu.service;

import org.springframework.beans.factory.annotation.Autowired;
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
}
