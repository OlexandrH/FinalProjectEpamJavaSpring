package ua.oblikchasu.model;

import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false, unique = true)
    private int id;

    @Column(name="name", nullable = false, unique = true, length = 32)
    private String name;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    public Activity() {
    }

    public Activity(@RequestParam("name") String name, @RequestParam("category") Category category) {
        this.name = name;
        this.category = category;
    }

    public Activity(int id) {
        this.id = id;
    }

    public Activity(int id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
