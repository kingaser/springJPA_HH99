package com.example.board.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}", message = "ID는 영어 소문자와 숫자로 이루어진, 4자~10자의 ID여야 합니다.")
    @Column(nullable = false, unique = true)
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z0-9\\d~!@#$%^&*()+|=]{8,15}$",

            message = "비밀번호는 영문자와 숫자로 이루어진, 8자~15자의 비밀번호여야 합니다.")
    @Column(nullable = false)
    private String password;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

}
