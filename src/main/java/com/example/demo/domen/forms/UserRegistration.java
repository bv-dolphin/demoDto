package com.example.demo.domen.forms;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UserRegistration {
    @NotBlank(message = "errors.user-registration.user_name.not-null")
    private String userName;

    @NotBlank(message = "errors.user-registration.email.not-null")
    private String email;

    @NotBlank(message = "errors.user_registration.password.not-null")
    private String password;



}
