package ua.oblikchasu.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersActivityController {

    private final UsersActivityService usersActivityService;
    private final UserService userService;
    private final ActivityService activityService;

    @Autowired
    public UsersActivityController(UsersActivityService userActivityService, UserService userService, ActivityService activityService){
        this.usersActivityService = userActivityService;
        this.userService = userService;
        this.activityService = activityService;
    }

    @GetMapping("/admin/usersactivity/list")
    public String getAll(Model model) {
        //List<UsersActivity> usersAct = usersActivityService.getAll()
        model.addAttribute("usersActivities", usersActivityService.getAll());
        model.addAttribute("usersActivityDTO", new UsersActivityDTO(0));
        return "";
    }

    @GetMapping("/admin/usersactivity/list/{id}")
    public String getByUser(@PathVariable int id, Model model) {
        List<UsersActivity> userActivities = usersActivityService.getForUser(id);
        List<UsersActivityDTO> usersActivityDTOList = new LinkedList<>();
        for(UsersActivity a: userActivities) {
            usersActivityDTOList.add(convertToUsersActivityDTO(a));
        }
        model.addAttribute("usersActivities", usersActivityDTOList);
        return "usersactivity-list";
    }

    @PostMapping("/admin/usersactivity/delete/{id}")
    public String delete (@PathVariable int id, Model model) {
        int userId=0;
        Optional<UsersActivity> optUsersActivity = usersActivityService.getById(id);
        if(optUsersActivity.isPresent()) {
            userId = optUsersActivity.get().getUser().getId();
            usersActivityService.deleteById(id);
            return "redirect:/admin/usersactivity/list/" + userId;
        }
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
            usersActivityService.update(usersActivity);
            return "redirect:/admin/usersactivity/list/" + userId;
        }
        return "redirect:/error";
    }

    @GetMapping("/personal/usersactivity/list")
    public String getPersonalActivities(Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        int id = user.getId();
        Optional<User> opt = userService.getById(user.getId());
        if (opt.isEmpty()) {
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

    @PostMapping("/personal/usersactivity/unbook/{id}")
    public String unbook (@PathVariable int id, Model model) {
        Optional<UsersActivity> optUsersActivity = usersActivityService.getById(id);
        if(optUsersActivity.isPresent()) {
            UsersActivity usersActivity = optUsersActivity.get();
            usersActivity.setStatus(UsersActivityStatus.UNBOOKED);
            usersActivityService.update(usersActivity);
            return "redirect:/personal/usersactivity/list";
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
            usersActivityService.add(optUsersActivity.get());
            return "redirect:/personal/usersactivity/list";
        }
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
