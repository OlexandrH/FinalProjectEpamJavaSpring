package ua.oblikchasu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.oblikchasu.model.Category;
import ua.oblikchasu.service.CategoryService;

import java.util.List;
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
        //model.addAttribute("categories", categoryService.getAll());
        Category categoryTemplate = new Category(0);
        model.addAttribute("category", categoryTemplate);
        //return "cat-list";
        return getPaginated(1, "id", "asc", model);
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

    @GetMapping("/list/page/{pageNo}")
    public String getPaginated (@PathVariable (value="pageNo") int pageNo,
                                @RequestParam ("sortBy") String sortBy,
                                @RequestParam ("sortOrder") String sortOrder,
                                Model model){
        int pageSize = 4;
        Page<Category> categoryPage = categoryService.getPaginated(pageNo, pageSize, sortBy, sortOrder);
        List<Category> categories = categoryPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        if(sortOrder.equals("asc")) {
            model.addAttribute("sortReverse", "desc");
        } else {
            model.addAttribute("sortReverse", "asc");
        }
        Category categoryTemplate = new Category(0);
        model.addAttribute("category", categoryTemplate);
        model.addAttribute("categories", categories);
        return "cat-list";
    }
}
