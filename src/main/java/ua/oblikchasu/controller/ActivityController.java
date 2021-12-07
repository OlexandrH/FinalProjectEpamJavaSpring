package ua.oblikchasu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.oblikchasu.dto.ActivityDTO;
import ua.oblikchasu.model.Activity;
import ua.oblikchasu.model.Category;
import ua.oblikchasu.service.ActivityService;
import ua.oblikchasu.service.CategoryService;

import java.util.Optional;

@RequestMapping("/admin/activity")
@Controller
public class ActivityController {

    private final ActivityService activityService;
    private final CategoryService categoryService;

    @Autowired
    public ActivityController (ActivityService activityService, CategoryService categoryService) {
        this.activityService = activityService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getAll (Model model)
    {
        model.addAttribute("activities", activityService.getAll());
        ActivityDTO activityDTO = new ActivityDTO(0);
        model.addAttribute("activityDTO", activityDTO);
        model.addAttribute("categories", categoryService.getAll());
        return "activity-list";
    }

    @PostMapping("/add")
    public String add (@ModelAttribute ActivityDTO activityDTO) {
        Optional<Activity> opt = convertToActivity(activityDTO);
        if(opt.isEmpty()) {
            return "redirect:/error";
        }
        activityService.add(opt.get());
        return "redirect:/admin/activity/list";
    }

    @PostMapping("/edit/{id}")
    public String update (@ModelAttribute ActivityDTO activityDTO, @PathVariable int id) {
        Optional<Activity> optOldActivity = activityService.getById(id);
        Optional<Activity> optUpdatedActivity = convertToActivity(activityDTO);
        if(optOldActivity.isEmpty() || optUpdatedActivity.isEmpty()) {
            return "redirect:/error";
        }
        Activity oldActivity = optOldActivity.get();
        Activity updatedActivity = optUpdatedActivity.get();
        oldActivity.setName(updatedActivity.getName());
        oldActivity.setCategory(updatedActivity.getCategory());
        if(!activityService.update(oldActivity)) {
            return "redirect:/error";
        }
        return "redirect:/admin/activity/list";
    }

    @PostMapping("/delete/{id}")
    public String delete (@PathVariable int id) {
        if(!activityService.deleteById(id)) {
            return "redirect:/error";
        }
        return "redirect:/admin/activity/list";
    }

    private Optional<Activity> convertToActivity(ActivityDTO activityDTO) {
        Optional<Category> opt = categoryService.getById(activityDTO.getCategoryId());
        if(opt.isEmpty()) return Optional.empty();
        Category category = opt.get();
        return Optional.of(new Activity(activityDTO.getId(), activityDTO.getName(), category));
    }

    private ActivityDTO convertToDTO(Activity activity) {
        return new ActivityDTO(activity.getId(), activity.getName(), activity.getCategory().getId());
    }
}
