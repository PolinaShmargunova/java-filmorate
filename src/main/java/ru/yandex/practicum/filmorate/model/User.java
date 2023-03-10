package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @With
    @DecimalMin("0")
    int id;
    @Email
    @NotBlank
    String email;
    @NotBlank
    String login;
    @With
    String name;
    String birthday;
    @With
    Set<Integer> friendsIds;
}