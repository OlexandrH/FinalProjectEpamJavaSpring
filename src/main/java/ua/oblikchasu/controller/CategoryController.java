package ua.oblikchasu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.oblikchasu.model.Category;
import ua.oblikchasu.service.CategoryService;
import java.util.Optional;

@RequestMapping("/admin/category")
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController (CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getAll (Model model) {
        model.addAttribute("categories", categoryService.getAll());
        Category categoryTemplate = new Category(0);
        model.addAttribute("category", categoryTemplate);
        return "cat-list";
    }

    @PostMapping("/add")
    public String add (@ModelAttribute Category category) {
        categoryService.add(category);
        return "redirect:/admin/category/list";
    }


    @PostMapping("/edit/{id}")
    public String update (@PathVariable int id, @ModelAttribute Category category) {
        Optional<Category> opt = categoryService.getById(id);
        if(opt.isEmpty()) {
            return "redirect:/error";
        }
        Category oldCategory = opt.get();
        oldCategory.setName(category.getName());

        if(!categoryService.update(oldCategory)) {
            return "redirect:/error";
        }
        return "redirect:/admin/category/list";
    }

    @PostMapping("/delete/{id}")
    public String delete (@PathVariable int id) {
        if(!categoryService.deleteById(id)) {
            return "redirect:/error";
        }
        return "redirect:/admin/category/list";
    }
}
