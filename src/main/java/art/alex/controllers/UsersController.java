package art.alex.controllers;

import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public @ResponseBody User getCurrentUser(final HttpServletRequest request, Principal principal) {
        final String currentUser = principal.getName();
        return usersRepository.findByUsername(currentUser);
    }

    @RequestMapping(value = "/me", method = RequestMethod.PUT)
    public @ResponseBody User saveCurrentUser(@Validated(User.ValidateOnUpdate.class) @RequestBody User user, final HttpServletRequest request, Principal principal) {
        //Nobody is going to save data to another user record. Not on my shift.
        final String currentUser = principal.getName();
        User correspondingUser = usersRepository.findByUsername(currentUser);
        user.setId(correspondingUser.getId());

        return usersRepository.save(user);
    }
}
