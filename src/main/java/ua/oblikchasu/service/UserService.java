package ua.oblikchasu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.oblikchasu.repository.UserRepository;
import ua.oblikchasu.model.User;
import ua.oblikchasu.model.UserRole;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll () {
        return userRepository.findAll();
    }

    public Optional<User> getById (int id) {
        return userRepository.findById(id);
    }

    public User add (User user) {
        user.setId(0);
        user.setRole(UserRole.USER);
        return userRepository.save(user);
    }

    public boolean update (User user) {
        if(!userRepository.existsById(user.getId())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public boolean delete (User user) {
        if(userRepository.existsById(user.getId())) {
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteById (int id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Page<User> getPaginated (int pageNo, int pageSize, String sortBy, String sortOrder) {
        Sort sort = Sort.by(sortBy).ascending();

        if(sortOrder.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return userRepository.findAll(pageable);
    }
}
