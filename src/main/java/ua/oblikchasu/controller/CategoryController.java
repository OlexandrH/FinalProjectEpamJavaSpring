package ua.oblikchasu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.oblikchasu.model.Category;
import ua.oblikchasu.service.CategoryService;
import ua.oblikchasu.logger.*;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/category")
@Controller
public class CategoryController {
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    @Autowired
    public CategoryController (CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getAll (Model model) {
        Category categoryTemplate = new Category(0);
        model.addAttribute("category", categoryTemplate);
        return getPaginated(1, "id", "asc", model);
    }

    @PostMapping("/add")
    public String add (@ModelAttribute Category category) {
        if(category == null) {
            logger.error(LogMsg.ACTIVITY_CATEGORY_ADD_FAIL);
            return "redirect:/error";
        }
        categoryService.add(category);
        logger.info(LogMsg.ACTIVITY_CATEGORY_ADDED, category);
        return "redirect:/admin/category/list";
    }


    @PostMapping("/edit/{id}")
    public String update (@PathVariable int id, @ModelAttribute Category category) {
        Optional<Category> opt = categoryService.getById(id);
        if(opt.isEmpty()) {
            logger.error(LogMsg.ACTIVITY_CATEGORY_NOT_FOUND, id);
            return "redirect:/error";
        }
        Category oldCategory = opt.get();
        oldCategory.setName(category.getName());

        if(!categoryService.update(oldCategory)) {
            logger.error(LogMsg.ACTIVITY_CATEGORY_UPDATE_FAIL, oldCategory);
            return "redirect:/error";
        }
        logger.info(LogMsg.ACTIVITY_CATEGORY_UPDATED, oldCategory);
        return "redirect:/admin/category/list";
    }

    @PostMapping("/delete/{id}")
    public String delete (@PathVariable int id) {
        if(!categoryService.deleteById(id)) {
            logger.error(LogMsg.ACTIVITY_CATEGORY_DELETE_FAIL, id);
            return "redirect:/error";
        }
        logger.info(LogMsg.ACTIVITY_CATEGORY_DELETED, id);
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
