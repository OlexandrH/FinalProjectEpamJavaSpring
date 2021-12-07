package ua.oblikchasu.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.oblikchasu.model.User;
import ua.oblikchasu.model.UserRole;
import ua.oblikchasu.security.UserDetailsImpl;

@RequestMapping("/redirect")
@Controller
public class DefaultRedirect {

    @GetMapping
    public String redirect () {
        String path="redirect:/personal/usersactivity/list";
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        if (user.getRole() == UserRole.ADMIN) {
            path = "redirect:/admin/user/list";
        }
        return path;
    }
}
