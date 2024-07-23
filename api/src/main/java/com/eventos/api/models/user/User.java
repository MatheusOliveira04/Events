package com.eventos.api.models.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "users")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id_user")
    private UUID id;

    @Column(name = "name_user")
    private String name;

    @Column(name = "email_user")
    private String email;

    @Column(name = "password_user")
    private String password;

    @Column(name = "roles_user")
    private String roles;
}
