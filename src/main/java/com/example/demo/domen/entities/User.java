package com.example.demo.domen.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @Email
    @Column(name = "email")
    private String email;

}
