package com.example.demo.controller;


import com.example.demo.domen.forms.UserRegistration;
import com.example.demo.domen.transfer.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.utils.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrController {

    private final UserService userService;

    public RegistrController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getSignUpPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String signUp(
            @Valid UserRegistration user,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getFieldErrors(bindingResult);
            model.mergeAttributes(errors);

            return "redirect:/public/registration";
        }

        if (!userService.registrationUser(user)) {
            model.addAttribute("numberBadge", "Number badge exists");

            return "redirect:/registration";
        }

        return "redirect:/login";
    }


    @GetMapping("/list")
    public String userList(Model model) {
        model.addAttribute("users", UserDto.from(userService.getAll()));
        return "user/userList";
    }

    @PostMapping("user")
    public String updateProfile(@Valid UserDto userDto,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsField = ControllerUtils.getFieldErrors(bindingResult);

            model.mergeAttributes(errorsField);
            model.addAttribute("users", userDto);

            return "user";

        } else {
            userService.updateProfile(userDto);
        }

        return "redirect:/user";

    }


}
