package ua.oblikchasu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.oblikchasu.model.User;
import ua.oblikchasu.model.UserRole;
import ua.oblikchasu.security.UserDetailsImpl;
import ua.oblikchasu.logger.*;

@RequestMapping("/redirect")
@Controller
public class DefaultRedirectController {
    private final Logger logger = LoggerFactory.getLogger(DefaultRedirectController.class);
    private static final String ADMIN_PAGE = "redirect:/admin/user/list";
    private static final String USER_PAGE = "redirect:/personal/usersactivity/list";

    @GetMapping
    public String redirect () {
        String path=USER_PAGE;
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        if (user.getRole() == UserRole.ADMIN) {
            path = ADMIN_PAGE;
        }
        logger.info(LogMsg.LOGGED_IN, user.getLogin(), user.getRole());
        return path;
    }
}
