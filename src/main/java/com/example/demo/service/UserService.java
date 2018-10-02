package com.example.demo.service;

import com.example.demo.domen.forms.UserRegistration;
import com.example.demo.domen.transfer.UserDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.domen.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean registrationUser(UserRegistration userRegistration) {
        if (repository.findByEmail(userRegistration.getEmail()).isPresent()) {
            return false;
        }

        this.repository.save(User.builder() //Конвертируем UserRegistration -> User с заполнением обязательных полей;
                .userName(userRegistration.getUserName())
                .email(userRegistration.getEmail())
                .password(userRegistration.getPassword())
                .active(false)
                .build()
        );

        return true;
    }

    public UserDto updateProfile(UserDto userCandidate) {
        User user = repository.findByEmail(userCandidate.getEmail()).get();

        boolean isEmailChanged = userCandidate.getEmail() != null && !userCandidate.getEmail().equals(user.getEmail());
        if (isEmailChanged) {
            if (!StringUtils.isEmpty(userCandidate.getEmail())) {
                user.setEmail(userCandidate.getEmail());
                user.setActive(false);
            }
        }

        boolean isPasswordChanged = userCandidate.getNewPassword()!= null && !userCandidate.getNewPassword().equals(user.getPassword());
        if (isPasswordChanged) {
            if (!StringUtils.isEmpty(userCandidate.getNewPassword())) {
                user.setPassword(userCandidate.getNewPassword());
            }
        }

        boolean isNikNameChanged = userCandidate.getUserName() != null && !userCandidate.getUserName().equals(user.getUserName());
        if (isNikNameChanged) {
            if (!StringUtils.isEmpty(userCandidate.getUserName())) {
                user.setUserName(userCandidate.getUserName());
            }
        }

        User userChangedProfile = repository.save(user);

        return UserDto.from(userChangedProfile);
    }

    public Iterable<User> getAll() {
        return repository.findAll();
    }

    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }
}
