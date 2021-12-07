package ua.oblikchasu.model;

import javax.persistence.*;

@Entity
@Table
public class UsersActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="status", nullable = false)
    private UsersActivityStatus status;

    @Column(name="time", nullable = false, updatable = false)
    private int time;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    public UsersActivity() {
    }

    public UsersActivity(int id) {
        this.id = id;
    }

    public UsersActivity(User user, Activity activity, int time, UsersActivityStatus status) {
        this.status = status;
        this.time = time;
        this.user = user;
        this.activity = activity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsersActivityStatus getStatus() {
        return status;
    }

    public void setStatus(UsersActivityStatus status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
