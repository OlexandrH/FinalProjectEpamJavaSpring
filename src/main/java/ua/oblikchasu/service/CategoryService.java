package ua.oblikchasu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.oblikchasu.repository.CategoryRepository;
import ua.oblikchasu.model.Category;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll () {
        return categoryRepository.findAll();
    }

//    public List<Category> getSortedPortion(String sortBy, int from, int amount, boolean order) {
//        return categoryDAO.findSortedPortion(sortBy, from, amount, order);
//    }

    public Optional<Category> getById (int id) {
        return categoryRepository.findById(id);
    }

    public Category add (Category category) {
        category.setId(0);
        return categoryRepository.save(category);
    }

    public boolean update (Category category) {
        if(!categoryRepository.existsById(category.getId())) {
            return false;
        }
        categoryRepository.save(category);
        return true;
    }

    public boolean delete (Category category) {
        if(! categoryRepository.existsById(category.getId())) {
            return false;
        }
        categoryRepository.delete(category);
        return true;
    }
    public boolean deleteById (int id) {
        if(! categoryRepository.existsById(id)) {
            return false;
        }
        categoryRepository.deleteById(id);
        return true;
    }


}
