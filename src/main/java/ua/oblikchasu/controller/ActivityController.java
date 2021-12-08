package ua.oblikchasu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.oblikchasu.dto.ActivityDTO;
import ua.oblikchasu.model.Activity;
import ua.oblikchasu.model.Category;
import ua.oblikchasu.service.ActivityService;
import ua.oblikchasu.service.CategoryService;
import ua.oblikchasu.logger.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/activity")
@Controller
public class ActivityController {
    private final Logger logger = LoggerFactory.getLogger(ActivityController.class);
    private final ActivityService activityService;
    private final CategoryService categoryService;

    @Autowired
    public ActivityController (ActivityService activityService, CategoryService categoryService) {
        this.activityService = activityService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public String getNonPaginated (Model model)
    {
        model.addAttribute("activities", activityService.getAll());
        ActivityDTO activityDTO = new ActivityDTO(0);
        model.addAttribute("activityDTO", activityDTO);
        model.addAttribute("categories", categoryService.getAll());
        return "activity-list";
    }

    @GetMapping("/list")
    public String getAll (Model model)
    {
        return getPaginated(1,"id", "asc", model);
    }

    @PostMapping("/add")
    public String add (@ModelAttribute ActivityDTO activityDTO) {
        Optional<Activity> opt = convertToActivity(activityDTO);
        if(opt.isEmpty()) {
            logger.info(LogMsg.ACTIVITY_ADD_FAIL);
            return "redirect:/error";
        }
        activityService.add(opt.get());
        logger.info(LogMsg.ACTIVITY_ADDED, opt.get());
        return "redirect:/admin/activity/list";
    }

    @PostMapping("/edit/{id}")
    public String update (@ModelAttribute ActivityDTO activityDTO, @PathVariable int id) {
        Optional<Activity> optOldActivity = activityService.getById(id);
        Optional<Activity> optUpdatedActivity = convertToActivity(activityDTO);
        if(optOldActivity.isEmpty()) {
            logger.error(LogMsg.ACTIVITY_NOT_FOUND, id);
            return "redirect:/error";
        }
        if(optUpdatedActivity.isEmpty()) {
            logger.error(LogMsg.ACTIVITY_UPDATE_FAIL, id);
            return "redirect:/error";
        }

        Activity oldActivity = optOldActivity.get();
        Activity updatedActivity = optUpdatedActivity.get();
        oldActivity.setName(updatedActivity.getName());
        oldActivity.setCategory(updatedActivity.getCategory());
        if(!activityService.update(oldActivity)) {
            logger.error(LogMsg.ACTIVITY_UPDATE_FAIL, id);
            return "redirect:/error";
        }
        logger.info(LogMsg.ACTIVITY_UPDATED, oldActivity);
        return "redirect:/admin/activity/list";
    }

    @PostMapping("/delete/{id}")
    public String delete (@PathVariable int id) {
        if(!activityService.deleteById(id)) {
            logger.error(LogMsg.ACTIVITY_DELETE_FAIL, id);
            return "redirect:/error";
        }
        logger.info(LogMsg.ACTIVITY_DELETED, id);
        return "redirect:/admin/activity/list";
    }

    @GetMapping("/list/page/{pageNo}")
    public String getPaginated (
            @PathVariable (value = "pageNo") int pageNo,
            @RequestParam ("sortBy") String sortBy,
            @RequestParam ("sortOrder") String sortOrder,
            Model model) {
        int pageSize = 5;

        Page<Activity> activityPage = activityService.getPaginated(pageNo, pageSize, sortBy, sortOrder);
        List<Activity> activities = activityPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", activityPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);

        if(sortOrder.equals("asc")) {
            model.addAttribute("sortReverse", "desc");
        } else {
            model.addAttribute("sortReverse", "asc");
        }

        ActivityDTO activityDTO = new ActivityDTO(0);
        model.addAttribute("activityDTO", activityDTO);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("activities", activities);
        return "activity-list";
    }

    @GetMapping("/stat")
    public String getAllWithStats (Model model) {

        return getAllWithStatsPaginated(1, "id", "asc", model);
    }

    @GetMapping("/stat/page/{pageNo}")
    public String getAllWithStatsPaginated (
            @PathVariable(value="pageNo") int pageNo,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortOrder") String sortOrder,
            Model model) {
            int pageSize = 7;
        Page<Activity> activityPage = activityService.getPaginated(pageNo, pageSize, sortBy, sortOrder);
        List<Activity> activities = activityPage.getContent();
        List<ActivityDTO> activityDTOList = new LinkedList<>();
        for(Activity a: activities) {
            ActivityDTO temp = convertToDTO(a);
            int totalTime = activityService.getTotalTime(a.getId());
            temp.setTotalTimeHours(totalTime/60);
            temp.setTotalTimeMin(totalTime%60);
            temp.setUserCount(activityService.getUserCount(a.getId()));
            activityDTOList.add(temp);
        }

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", activityPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);

        if(sortOrder.equals("asc")) {
            model.addAttribute("sortReverse", "desc");
        } else {
            model.addAttribute("sortReverse", "asc");
        }
        model.addAttribute("activities", activityDTOList);
        return "usersactivity-all";
    }

    private Optional<Activity> convertToActivity(ActivityDTO activityDTO) {
        Optional<Category> opt = categoryService.getById(activityDTO.getCategoryId());
        if(opt.isEmpty()) return Optional.empty();
        Category category = opt.get();
        return Optional.of(new Activity(activityDTO.getId(), activityDTO.getName(), category));
    }

    private ActivityDTO convertToDTO(Activity activity) {
        return new ActivityDTO(activity.getId(), activity.getName(), activity.getCategory().getId(), activity.getCategory().getName());
    }
}
