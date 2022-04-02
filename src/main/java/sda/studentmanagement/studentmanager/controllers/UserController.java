package sda.studentmanagement.studentmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.domain.request.UserDto;
import sda.studentmanagement.studentmanager.domain.request.UserLoginDto;
import sda.studentmanagement.studentmanager.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/registration")
    public String userRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "user-registration";
    }

    @GetMapping("/user/login")
    public String userLoginForm(WebRequest request, Model model) {
        UserLoginDto userDto = new UserLoginDto();
        model.addAttribute("user", userDto);
        return "user-login";
    }

    @GetMapping("/user/logout")
    public String userLogoutForm() {
        return "redirect:/user/login";
    }

    @PostMapping("/user/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user-registration";
        }

        User user = userService.createNewUser(userDto);
        return "redirect:/";
    }
}
