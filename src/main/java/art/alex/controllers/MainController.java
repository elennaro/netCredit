package art.alex.controllers;


import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import art.alex.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    protected UserValidator userValidator;

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        if (binder.getTarget() instanceof User)
            binder.addValidators(userValidator);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute("user") User user,
            final BindingResult bindingResult,
            RedirectAttributes attr,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            logger.warn("Validation exception.", bindingResult.getAllErrors());
            attr.addFlashAttribute("bindingResult", bindingResult);
            attr.addFlashAttribute("user", user);
            return "register";
        }

        usersRepository.registerUser(user);

        return "redirect:/profile";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@ModelAttribute("user") User user) {
        return "register";
    }

}
