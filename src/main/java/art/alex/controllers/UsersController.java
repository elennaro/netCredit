package art.alex.controllers;

import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import art.alex.services.CreditDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/api/users")
public class UsersController {

    private final UsersRepository usersRepository;
    private final CreditDataService creditDataService;

    @Autowired
    public UsersController(UsersRepository usersRepository, CreditDataService creditDataService) {
        this.creditDataService = creditDataService;
        this.usersRepository = usersRepository;
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public @ResponseBody User getCurrentUser(@AuthenticationPrincipal User activeUser) {
        return creditDataService.setCreditLimit(activeUser);
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
