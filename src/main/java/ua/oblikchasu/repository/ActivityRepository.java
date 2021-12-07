package ua.oblikchasu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.oblikchasu.model.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository <Activity, Integer> {
    @Query(value= "SELECT * FROM activity WHERE category_id = ?1", nativeQuery = true)
    List<Activity> findByCategory(int catId);
}
