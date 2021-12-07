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

    @Query(value = "select users_activity.* from users_activity left join activity on users_activity.activity_id = activity.id left join category on activity.category_id = category.id where user_id = ?1 order by category.name ASC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByCategoryAsc (int userId, int from, int amount);

    @Query(value = "select users_activity.* from users_activity left join activity on users_activity.activity_id = activity.id left join category on activity.category_id = category.id where user_id = ?1 order by category.name DESC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByCategoryDesc (int userId, int from, int amount);

    @Query(value="SELECT * FROM users_activity WHERE user_id = ?1 ORDER BY id ASC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByIdAsc (int userId, int from, int amount);

    @Query(value="SELECT * FROM users_activity WHERE user_id = ?1 ORDER BY id DESC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByIdDesc (int userId, int from, int amount);

    @Query(value = "select distinct users_activity.* from users_activity inner join activity on users_activity.activity_id = activity.id where user_id = ?1 order by activity.name ASC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByActivityAsc (int userId, int from, int amount);

    @Query(value = "select distinct users_activity.* from users_activity inner join activity on users_activity.activity_id = activity.id where user_id = ?1 order by activity.name DESC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByActivityDesc (int userId, int from, int amount);

    @Query(value="SELECT * FROM users_activity WHERE user_id = ?1 ORDER BY time ASC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByTimeAsc (int userId, int from, int amount);

    @Query(value="SELECT * FROM users_activity WHERE user_id = ?1 ORDER BY time DESC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByTimeDesc (int userId, int from, int amount);

    @Query(value="SELECT * FROM users_activity WHERE user_id = ?1 ORDER BY status ASC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByStatusAsc (int userId, int from, int amount);

    @Query(value="SELECT * FROM users_activity WHERE user_id = ?1 ORDER BY status DESC LIMIT ?2, ?3", nativeQuery = true)
    List<UsersActivity> findForUserSortedByStatusDesc (int userId, int from, int amount);
    @Query(value="SELECT COUNT(*) FROM users_activity WHERE user_id = ?1", nativeQuery = true)
    int countForUser(int userId);
}
