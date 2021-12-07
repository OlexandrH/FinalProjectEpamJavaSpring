package ua.oblikchasu.dto;

public class ActivityDTO {
    private int id;
    private String name;
    private int categoryId;

    public ActivityDTO(int id, String name, int categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
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

    @Override
    public String toString() {
        return "ActivityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
