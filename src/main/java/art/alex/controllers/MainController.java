package art.alex.controllers;


import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import art.alex.services.CustomUserDetailsService;
import art.alex.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    protected UserValidator userValidator;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        if (binder.getTarget() instanceof User)
            binder.addValidators(userValidator);
    }

    private boolean autoLogin(User user, HttpServletRequest request){
        try{
            // generate session if one doesn't exist
            request.getSession();
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, CustomUserDetailsService.getDefaultAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        catch (Exception e){
            logger.error("Error loging registered user in", e);
            return false;
        }

        return true;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @Validated(User.ValidateOnCreate.class) @ModelAttribute("user") User user,
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

        return this.autoLogin(user, request) ? "redirect:/profile" : "redirect:/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@ModelAttribute("user") User user) {
        return "register";
    }

}
