package com.example.demo.domen.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

    private String login;

    private String password;

    private Boolean rememberMe;
}
