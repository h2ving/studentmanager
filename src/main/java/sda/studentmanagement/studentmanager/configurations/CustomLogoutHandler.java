package sda.studentmanagement.studentmanager.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import sda.studentmanagement.studentmanager.services.UserCache;
import sda.studentmanagement.studentmanager.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//!!!!! DELETE We don't log out in server side, client just destroys the token in session storage and the we can't make any requests
/*@Service
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private UserCache userCache;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authenticatedUserName = UserUtils.getAuthenticatedUserName();
        userCache.evictUser(authenticatedUserName);
    }
}*/
