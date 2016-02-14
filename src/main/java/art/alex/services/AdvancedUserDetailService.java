package art.alex.services;


import art.alex.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public interface AdvancedUserDetailService extends UserDetailsService {

    /**
     * Method to use for automatically login user in spring auth
     * @param user User to login as
     * @param request current request
     * @return true is user can be authenticated of false if not
     */
    boolean autoLogin(User user, HttpServletRequest request);

}
