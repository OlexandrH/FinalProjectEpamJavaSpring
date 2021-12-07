package ua.oblikchasu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.oblikchasu.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Integer> {

}
