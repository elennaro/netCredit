package art.alex.controllers;


import art.alex.entities.User;
import art.alex.repositories.UsersRepository;
import art.alex.services.AdvancedUserDetailService;
import art.alex.services.ImageUploadService;
import art.alex.validators.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final UsersRepository usersRepository;
    private final UserValidator userValidator;
    private final AdvancedUserDetailService userDetailService;
    private final ImageUploadService imageUploadService;

    @Autowired
    public MainController(UsersRepository usersRepository,
                          UserValidator userValidator,
                          AdvancedUserDetailService userDetailService,
                          ImageUploadService imageUploadService) {
        this.usersRepository = usersRepository;
        this.userValidator = userValidator;
        this.userDetailService = userDetailService;
        this.imageUploadService = imageUploadService;
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        if (binder.getTarget() instanceof User)
            binder.addValidators(userValidator);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @Validated(User.ValidateOnCreate.class) @ModelAttribute("user") User user,
            final BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (image != null && !image.isEmpty()) {
            //For purposes of time I won't generate file name in this case, however I know that I should not pass original name
            String filename = image.getOriginalFilename();
            if (!imageUploadService.isValidImage(image))
                bindingResult.rejectValue("avatar", "user.avatar", "Bad image format!");
            else if (!bindingResult.hasErrors() && imageUploadService.saveImage(filename, image))
                user.setAvatar(filename);
        }

        if (bindingResult.hasErrors()) {
            logger.warn("Validation exception.", bindingResult.getAllErrors());
            response.setStatus(SC_BAD_REQUEST);
            return "register";
        }

        usersRepository.registerUser(user);

        return userDetailService.autoLogin(user, request) ? "redirect:/profile" : "redirect:/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@ModelAttribute("user") User user) {
        return "register";
    }

}
