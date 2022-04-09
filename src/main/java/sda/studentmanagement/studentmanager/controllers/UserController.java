package sda.studentmanagement.studentmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.domain.request.UserDto;
import sda.studentmanagement.studentmanager.domain.request.UserLoginDto;
import sda.studentmanagement.studentmanager.repositories.UserRepository;
import sda.studentmanagement.studentmanager.services.UserService;
import sda.studentmanagement.studentmanager.utils.RandomThings;
import sda.studentmanagement.studentmanager.utils.UserUtils;
import sda.studentmanagement.studentmanager.utils.generationStrategy.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepo;

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

    @GetMapping(value = "/", produces = "application/json")
    @ResponseBody
    public User showCurrentUser(Model model) throws Exception {

        User user = userRepo.getUserByEmail(UserUtils.getAuthenticatedUserName());
            return user;
    }

    @GetMapping("/admin/userlist")
    @ResponseBody
    public List<User> returnUserRepo() {

        List<User> user = (List<User>) userRepo.findAll(); //Direct access to the repo

        return user;
    }

    @GetMapping(value = "/admin/user/{id}", produces = "application/json")
    @ResponseBody
    public User returnUser(@PathVariable("id") int id) {

        User user = userRepo.findById(id).orElse(null); //Direct access to the repo

        return user;
    }

    @DeleteMapping(value = "/admin/user/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable("id") int id) {
        if (userRepo.findById(id) != null) {
            userRepo.deleteById(id);
            return "Done";
        } else return "None";
    }

    @PostMapping(value = "/admin/addUser", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public String addUser(@RequestBody UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Booop!";
        }
        userService.createNewUser(userDto);
        return userDto.toString();
    }

    @RequestMapping(value = "/admin/drop")
    @ResponseBody
    public void dropUserRepo() {
        userRepo.deleteAll();
    }

    @RequestMapping(value = "/admin/addRandomUser")
    @ResponseBody
    public User addRandomUser() {
        User user = new User();
        user = RandomThings.generateRandomUser();
        userRepo.save(user);
        return user;
    }

    @RequestMapping(value = "/admin/addALotOfRandomUsers")
    @ResponseBody
    public void addALotOfRandomUsers() {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user = RandomThings.generateRandomUser();
            userList.add(user);
        }
        userRepo.saveAll(userList);
    }
}
