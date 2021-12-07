package ua.oblikchasu.dto;

public class ActivityDTO {
    private int id;
    private String name;
    private int categoryId;
    private String categoryName="";
    private int totalTimeHours=0;
    private int totalTimeMin=0;
    private int userCount=0;

    public ActivityDTO(int id, String name, int categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;

    }

    public ActivityDTO(int id, String name, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public ActivityDTO(int id) {
        this.id = id;
    }

    public ActivityDTO(String name, int categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public ActivityDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalTimeHours() {
        return totalTimeHours;
    }

    public void setTotalTimeHours(int totalTimeHours) {
        this.totalTimeHours = totalTimeHours;
    }

    public int getTotalTimeMin() {
        return totalTimeMin;
    }

    public void setTotalTimeMin(int totalTimeMin) {
        this.totalTimeMin = totalTimeMin;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "ActivityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
