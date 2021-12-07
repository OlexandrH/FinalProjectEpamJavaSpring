package ua.oblikchasu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.oblikchasu.model.Activity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository <Activity, Integer> {
    @Query(value= "SELECT * FROM activity WHERE category_id = ?1", nativeQuery = true)
    List<Activity> findByCategory(int catId);

    @Query(value ="SELECT * FROM activity INNER JOIN category ON category.id = activity.category_id ORDER BY category.name desc LIMIT ?1, ?2",
            nativeQuery = true)
    List<Activity> findSortedByCategoryNameDesc(int from, int amount);

    @Query(value ="SELECT * FROM activity INNER JOIN category ON category.id = activity.category_id ORDER BY category.name asc LIMIT ?1, ?2",
            nativeQuery = true)
    List<Activity> findSortedByCategoryNameAsc(int from, int amount);

    @Query(value="select activity.id, activity.name, activity_id, category_id, sum(users_activity.time) time from \n" +
            "activity left join users_activity on users_activity.activity_id = activity.id \n" +
            " group by users_activity.activity_id order by time asc limit ?1, ?2", nativeQuery = true)
    List<Activity> findSortedByTotalTimeAsc(int from, int amount);

    @Query(value="select activity.id, activity.name, activity_id, category_id, sum(users_activity.time) time from \n" +
            "activity left join users_activity on users_activity.activity_id = activity.id \n" +
            " group by users_activity.activity_id order by time desc limit ?1, ?2", nativeQuery = true)
    List<Activity> findSortedByTotalTimeDesc(int from, int amount);

    @Query(value="select activity.id, activity.name, activity_id, category_id, count(distinct user_id) userCount from \n" +
            "activity left join users_activity on users_activity.activity_id = activity.id \n" +
            " group by users_activity.activity_id order by userCount asc limit ?1, ?2", nativeQuery = true)
    List<Activity> findSortedByUserCountAsc(int from, int amount);

    @Query(value="select activity.id, activity.name, activity_id, category_id, count(distinct user_id) userCount from \n" +
            "activity left join users_activity on users_activity.activity_id = activity.id \n" +
            " group by users_activity.activity_id order by userCount desc limit ?1, ?2", nativeQuery = true)
    List<Activity> findSortedByUserCountDesc(int from, int amount);

    @Query(value="SELECT SUM(time) from users_activity WHERE activity_id = ?1", nativeQuery = true)
    Optional<Integer> findTotalTimeByActivity(int activityId);

    @Query(value="SELECT COUNT(DISTINCT user_id) from users_activity WHERE activity_id = ?1", nativeQuery = true)
    Optional<Integer> findUserCountByActivity(int activityId);
}
