package sda.studentmanagement.studentmanager.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static String getAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername() : null;
    }
}
