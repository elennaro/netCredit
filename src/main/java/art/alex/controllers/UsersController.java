package art.alex.controllers;

import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public @ResponseBody User getCurrentUser(@AuthenticationPrincipal User activeUser) {
        return usersRepository.findById(activeUser.getId());
    }

    @RequestMapping(value = "/me", method = RequestMethod.PUT)
    public @ResponseBody User saveCurrentUser(@Validated(User.ValidateOnUpdate.class) @RequestBody User user, @AuthenticationPrincipal User activeUser) {
        //Nobody is going to save data to another user record. Not on my shift.
        User correspondingUser = usersRepository.findById(activeUser.getId());
        correspondingUser.setPhoneNumber(user.getPhoneNumber());
        correspondingUser.setUsername(user.getUsername());
        correspondingUser.setMonthlySalary(user.getMonthlySalary());
        correspondingUser.setCurrentRemainingLiabilities(user.getCurrentRemainingLiabilities());

        return usersRepository.save(correspondingUser);
    }
}
