package ua.oblikchasu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.oblikchasu.dto.UsersActivityDTO;
import ua.oblikchasu.model.*;
import ua.oblikchasu.security.UserDetailsImpl;
import ua.oblikchasu.service.ActivityService;
import ua.oblikchasu.service.UserService;
import ua.oblikchasu.service.UsersActivityService;
import ua.oblikchasu.logger.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersActivityController {

    private final Logger logger = LoggerFactory.getLogger(UsersActivityController.class);
    private final UsersActivityService usersActivityService;
    private final UserService userService;
    private final ActivityService activityService;

    @Autowired
    public UsersActivityController(UsersActivityService userActivityService, UserService userService, ActivityService activityService){
        this.usersActivityService = userActivityService;
        this.userService = userService;
        this.activityService = activityService;
    }

    @GetMapping("/admin/usersactivity/all")
    public String getAll(Model model) {
        List<UsersActivity> usersAct = usersActivityService.getAll();
        model.addAttribute("usersActivities", usersActivityService.getAll());
        model.addAttribute("usersActivityDTO", new UsersActivityDTO(0));
        return "usersactivity-list";
    }

    @GetMapping("/admin/usersactivity/list/{id}")
    public String getByUser(@PathVariable int id, Model model) {
        return getByUserSorted(id, 1, "id", "desc", model);
    }

    @GetMapping("/admin/usersactivity/list/{id}/page/{pageNo}")
    public String getByUserSorted(
            @PathVariable (value="id")int id,
            @PathVariable (value="pageNo") int pageNo,
            @RequestParam ("sortBy") String sortBy,
            @RequestParam ("sortOrder") String sortOrder,
            Model model) {
        int pageSize = 5;
        Page<UsersActivity> usersActivityPage = usersActivityService.getPaginated(id, pageNo, pageSize, sortBy, sortOrder);
        List<UsersActivity> userActivities = usersActivityPage.getContent();
        List<UsersActivityDTO> usersActivityDTOList = new LinkedList<>();
        for(UsersActivity a: userActivities) {
            usersActivityDTOList.add(convertToUsersActivityDTO(a));
        }

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", usersActivityPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);

        if(sortOrder.equals("asc")) {
            model.addAttribute("sortReverse", "desc");
        } else {
            model.addAttribute("sortReverse", "asc");
        }

        model.addAttribute("userId", id);
        model.addAttribute("usersActivities", usersActivityDTOList);
        return "usersactivity-list";
    }


    @PostMapping("/admin/usersactivity/delete/{id}")
    public String delete (@PathVariable int id, Model model) {
        int userId=0;
        Optional<UsersActivity> optUsersActivity = usersActivityService.getById(id);
        if(optUsersActivity.isPresent()) {
            userId = optUsersActivity.get().getUser().getId();

            if(usersActivityService.deleteById(id)){
                logger.info(LogMsg.USERS_ACTIVITY_DELETED, id);
                return "redirect:/admin/usersactivity/list/" + userId;
            }
            logger.error(LogMsg.USERS_ACTIVITY_DELETE_FAIL, id);
            return "redirect:/error";
        }
        logger.error(LogMsg.USERS_ACTIVITY_NOT_FOUND, id);
        logger.error(LogMsg.USERS_ACTIVITY_DELETE_FAIL, id);
        return "redirect:/error";
    }

    @PostMapping("/admin/usersactivity/accept/{id}")
    public String accept (@PathVariable int id, Model model) {
        int userId;
        Optional<UsersActivity> optUsersActivity = usersActivityService.getById(id);
        if(optUsersActivity.isPresent()) {
            UsersActivity usersActivity = optUsersActivity.get();
            userId = usersActivity.getUser().getId();
            usersActivity.setStatus(UsersActivityStatus.ACCEPTED);

            if(usersActivityService.update(usersActivity)){
                logger.info(LogMsg.USERS_ACTIVITY_UPDATED, id, usersActivity.getStatus());
                return "redirect:/admin/usersactivity/list/" + userId;
            }
            logger.error(LogMsg.USERS_ACTIVITY_UPDATE_FAIL, id);
            return "redirect:/error";
        }
        logger.error(LogMsg.USERS_ACTIVITY_UPDATE_FAIL, id);
        logger.error(LogMsg.USERS_ACTIVITY_NOT_FOUND, id);
        return "redirect:/error";
    }

    @GetMapping("/personal/usersactivity/all")
    public String getAllPersonalActivities(Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        int id = user.getId();
        Optional<User> opt = userService.getById(user.getId());
        if (opt.isEmpty()) {
            logger.error(LogMsg.USERS_ACTIVITY_GET_FAIL, id);
            logger.error(LogMsg.USER_NOT_FOUND_ID, id);
            return "redirect:/error";
        }
        user = opt.get();
        List<UsersActivity> userActivities = usersActivityService.getForUser(id);
        List<UsersActivityDTO> usersActivityDTOList = new LinkedList<>();
        for(UsersActivity a: userActivities) {
            usersActivityDTOList.add(convertToUsersActivityDTO(a));
        }
        List<Activity> activities = activityService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("activities", activities);
        model.addAttribute("usersActivities", usersActivityDTOList);
        return "usersactivity-personal";
    }

    @GetMapping("/personal/usersactivity/list")
    public String getPersonalActivitiesDefault (Model model) {
        return getPersonalActivities(1, "id", "desc", model);
    }

    @GetMapping("/personal/usersactivity/list/page/{pageNo}")
    public String getPersonalActivities(
            @PathVariable (value="pageNo") int pageNo,
            @RequestParam ("sortBy") String sortBy,
            @RequestParam ("sortOrder") String sortOrder,
            Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        int id = user.getId();
        Optional<User> opt = userService.getById(user.getId());
        if (opt.isEmpty()) {
            logger.error(LogMsg.USERS_ACTIVITY_GET_FAIL, id);
            logger.error(LogMsg.USER_NOT_FOUND_ID, id);
            return "redirect:/error";
        }
        user = opt.get();
        int pageSize = 5;
        Page<UsersActivity> usersActivityPage = usersActivityService.getPaginated(id, pageNo, pageSize, sortBy, sortOrder);
        List<UsersActivity> userActivities = usersActivityPage.getContent();
        List<UsersActivityDTO> usersActivityDTOList = new LinkedList<>();
        for(UsersActivity a: userActivities) {
            usersActivityDTOList.add(convertToUsersActivityDTO(a));
        }

        List<Activity> activities = activityService.getAll();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", usersActivityPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);

        if(sortOrder.equals("asc")) {
            model.addAttribute("sortReverse", "desc");
        } else {
            model.addAttribute("sortReverse", "asc");
        }

        model.addAttribute("user", user);
        model.addAttribute("activities", activities);
        model.addAttribute("usersActivities", usersActivityDTOList);

        return "usersactivity-personal";
    }


    @PostMapping("/personal/usersactivity/unbook/{id}")
    public String unbook (@PathVariable int id, Model model) {
        Optional<UsersActivity> optUsersActivity = usersActivityService.getById(id);
        if(optUsersActivity.isPresent()) {
            UsersActivity usersActivity = optUsersActivity.get();
            usersActivity.setStatus(UsersActivityStatus.UNBOOKED);
            if(usersActivityService.update(usersActivity)) {
                logger.info(LogMsg.USERS_ACTIVITY_UPDATED, id, usersActivity.getStatus());
                return "redirect:/personal/usersactivity/list";
            }
            logger.error(LogMsg.USERS_ACTIVITY_UPDATE_FAIL, id);
            return "redirect:/error";
        }
        return "redirect:/error";
    }

    @PostMapping("/personal/usersactivity/add")
    public String add (
            @RequestParam("activityId") int activityId,
            @RequestParam("timeHours") int timeHours,
            @RequestParam("timeMin") int timeMin) {
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        int userId = user.getId();
        UsersActivityDTO usersActivityDTO = new UsersActivityDTO(0, userId, activityId, null, null, timeHours, timeMin, UsersActivityStatus.BOOKED);
        Optional<UsersActivity> optUsersActivity = convertToUsersActivity(usersActivityDTO);
        if(optUsersActivity.isPresent()) {
            UsersActivity usersActivity = usersActivityService.add(optUsersActivity.get());
            logger.info(LogMsg.USERS_ACTIVITY_ADDED, usersActivity.getId());
            return "redirect:/personal/usersactivity/list";
        }
        logger.error(LogMsg.USERS_ACTIVITY_ADD_FAIL);
        return "redirect:/error";
    }


    public Optional<UsersActivity> convertToUsersActivity(UsersActivityDTO usersActivityDTO) {
        Optional<Activity> optActivity = activityService.getById(usersActivityDTO.getActivityId());
        Optional<User> optUser = userService.getById(usersActivityDTO.getUserId());
        if(optActivity.isEmpty() || optUser.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new UsersActivity(optUser.get(), optActivity.get(), usersActivityDTO.getTimeHours()*60 + usersActivityDTO.getTimeMin(), usersActivityDTO.getStatus()));
    }

    public UsersActivityDTO convertToUsersActivityDTO (UsersActivity usersActivity) {
        return new UsersActivityDTO(
                usersActivity.getId(),
                usersActivity.getUser().getId(),
                usersActivity.getActivity().getId(),
                usersActivity.getActivity().getName(),
                usersActivity.getActivity().getCategory().getName(),
                usersActivity.getTime()/60,
                usersActivity.getTime()%60,
                usersActivity.getStatus()
        );
    }


}
