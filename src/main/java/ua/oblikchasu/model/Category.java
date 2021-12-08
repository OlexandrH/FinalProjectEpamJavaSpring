package ua.oblikchasu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.util.List;

@Entity(name="category")
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", unique = true, nullable = false)
    private int id;

    @Column(name="name", unique = true, nullable = false, length = 32)
    private String name;

    public Category(@RequestParam("cat_id")int id) {
        this.id = id;
    }

    public Category(@RequestParam("id") int id, @RequestParam("name") String name) {
        this.id = id;
        this.name = name;
    }

    public Category(@RequestParam("name") String name) {
        this.name = name;
    }

    public Category() {
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

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
