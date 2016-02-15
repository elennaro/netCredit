package art.alex.controllers;

import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import art.alex.services.CreditDataService;
import art.alex.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/api/users")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersRepository usersRepository;
    private final CreditDataService creditDataService;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public UsersController(UsersRepository usersRepository,
                           CreditDataService creditDataService,
                           CustomUserDetailsService customUserDetailsService) {
        this.creditDataService = creditDataService;
        this.usersRepository = usersRepository;
        this.userDetailsService = customUserDetailsService;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public @ResponseBody User getCurrentUser(@AuthenticationPrincipal User activeUser) {
        //Nobody is going to save data to another user record. Not on my shift.
        User correspondingUser = usersRepository.findById(activeUser.getId());

        return creditDataService.setCreditLimit(correspondingUser);
    }

    @RequestMapping(value = "/me/updatePassword", method = RequestMethod.PUT)
    public @ResponseBody User updatePassword(@Validated(User.ValidateOnUpdatePassword.class) @RequestBody User user, @AuthenticationPrincipal User activeUser) {
        //Nobody is going to save data to another user record. Not on my shift.
        User correspondingUser = usersRepository.findById(activeUser.getId());

        if (user.getPassword() != null)
            correspondingUser.setPassword(user.getPassword());

        return creditDataService.setCreditLimit(usersRepository.updatePassword(correspondingUser));
    }

    @RequestMapping(value = "/me", method = RequestMethod.PUT)
    public @ResponseBody User saveCurrentUser(@Validated(User.ValidateOnUpdate.class) @RequestBody User user, @AuthenticationPrincipal User activeUser) {
        //Nobody is going to save data to another user record. Not on my shift.
        User correspondingUser = usersRepository.findById(activeUser.getId());

        if (user.getPhoneNumber() != null)
            correspondingUser.setPhoneNumber(user.getPhoneNumber());
        if (user.getUsername() != null)
            correspondingUser.setUsername(user.getUsername());
        if (user.getMonthlySalary() != null)
            correspondingUser.setMonthlySalary(user.getMonthlySalary());
        if (user.getCurrentRemainingLiabilities() != null)
            correspondingUser.setCurrentRemainingLiabilities(user.getCurrentRemainingLiabilities());

        return creditDataService.setCreditLimit(usersRepository.save(correspondingUser));
    }
}
