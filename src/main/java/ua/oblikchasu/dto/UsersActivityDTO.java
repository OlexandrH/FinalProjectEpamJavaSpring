package ua.oblikchasu.dto;

import ua.oblikchasu.model.UsersActivityStatus;

public class UsersActivityDTO {
    private int id;
    private int userId;
    private int activityId;
    private String activityName;
    private String categoryName;
    private int timeHours;
    private int timeMin;
    private UsersActivityStatus status;

    public UsersActivityDTO() {
    }

    public UsersActivityDTO(int id) {
        this.id = id;
    }

    public UsersActivityDTO(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public UsersActivityDTO(int id, int userId, int activityId, String activityName, String categoryName, int timeHours, int timeMin, UsersActivityStatus status) {
        this.id = id;
        this.userId = userId;
        this.activityId = activityId;
        this.activityName = activityName;
        this.categoryName = categoryName;
        this.timeHours = timeHours;
        this.timeMin = timeMin;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public UsersActivityStatus getStatus() {
        return status;
    }

    public void setStatus(UsersActivityStatus status) {
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTimeHours() {
        return timeHours;
    }

    public void setTimeHours(int timeHours) {
        this.timeHours = timeHours;
    }

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }
}
