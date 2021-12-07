package ua.oblikchasu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.oblikchasu.model.UsersActivity;

import java.util.List;

@Repository
public interface UsersActivityRepository extends JpaRepository <UsersActivity, Integer>{
    @Query(value="SELECT * FROM users_activity WHERE user_id = ?1", nativeQuery = true)
    List<UsersActivity> findForUser (int userId);
}
