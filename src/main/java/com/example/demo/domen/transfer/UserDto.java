package com.example.demo.domen.transfer;

import com.example.demo.domen.validator.FieldMatch;
import com.example.demo.domen.validator.NotNullIfAnotherFieldHasValue;
import com.google.common.collect.Lists;
import com.example.demo.domen.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldMatch(first = "newPassword", second = "repeatPassword", message = "errors.user_dto.repeat_password.must-match")
@NotNullIfAnotherFieldHasValue(fieldName = "newPassword", dependFieldName = "oldPassword", message = "errors.user_dto.old_password.not-null")
public class UserDto {

    private Long id;

    @NotBlank(message = "errors.user_dto.user_name.not-null")
    private String userName;

    @Email(message = "errors.user_dto.email.not-null")
    private String email;

    private Boolean active;

    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .active(user.getActive())
                .build();
    }

    public static List<UserDto> from(Iterable<User> usersIterable) {

        List<User> users = Lists.newArrayList( usersIterable );

        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }
}
