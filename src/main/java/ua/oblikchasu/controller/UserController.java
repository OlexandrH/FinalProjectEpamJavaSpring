package ua.oblikchasu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.oblikchasu.model.User;
import ua.oblikchasu.security.UserDetailsImpl;
import ua.oblikchasu.service.UserService;

import java.util.List;
import java.util.Optional;


@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("admin/user/list")
    public String getAll (Model model) {
        //model.addAttribute("users", userService.getAll());
        //return "user-list";
        return getPaginated(1, "id", "asc", model);
    }

    @GetMapping("/add")
    public String register (Model model) {
        User user = new User(0);
        model.addAttribute("user", user);
        return "user-add";
    }

    @PostMapping("/add")
    public String add (@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/login";
    }


    @GetMapping("/admin/user/edit/{id}")
    public String edit (@PathVariable int id, Model model) {
        Optional<User> opt = userService.getById(id);
        if (opt.isEmpty()) {
            return "redirect:/error";
        }
        User editedUser = opt.get();
        model.addAttribute("user", editedUser);
        return "user-edit";
    }

    @PostMapping("/admin/user/edit/{id}")
    public String update(@PathVariable int id, @ModelAttribute ("user") User user, Model model) {
        Optional<User> opt = userService.getById(id);
        if (opt.isEmpty()) {
            return "redirect:/error";
        }
        User oldUser = opt.get();
        oldUser.setPassword(user.getPassword());
        oldUser.setName(user.getName());
        if(!userService.update(oldUser)) {
            return "redirect:/error";
        }
        return "redirect:/admin/user/edit/" + id;
    }

    @PostMapping("/admin/user/delete/{id}")
    public String delete (@PathVariable int id) {
        if(!userService.deleteById(id)) {
            return "redirect:/error";
        }
        return "redirect:/admin/user/list";
    }

    @GetMapping("/personal/user/edit")
    public String personalEdit (Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "personal-edit";
    }

    @PostMapping("/personal/user/edit/{id}")
    public String personalUpdate(@PathVariable int id, @ModelAttribute ("user") User user, Model model) {
        Optional<User> opt = userService.getById(id);
        if (opt.isEmpty()) {
            return "redirect:/error";
        }
        User oldUser = opt.get();
        oldUser.setPassword(user.getPassword());
        oldUser.setName(user.getName());
        if(!userService.update(oldUser)) {
            return "redirect:/error";
        }
        return "redirect:/personal/usersactivity/list";
    }

    @GetMapping("/admin/user/list/page/{pageNo}")
    public String getPaginated (
            @PathVariable (value = "pageNo") int pageNo,
            @RequestParam ("sortBy") String sortBy,
            @RequestParam ("sortOrder") String sortOrder,
            Model model) {
        int pageSize = 5;
        Page<User> userPage = userService.getPaginated(pageNo, pageSize, sortBy, sortOrder);
        List<User> users = userPage.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        if(sortOrder.equals("asc")) {
            model.addAttribute("sortReverse", "desc");
        } else {
            model.addAttribute("sortReverse", "asc");
        }
        model.addAttribute("users", users);
        return "user-list";
    }
}
