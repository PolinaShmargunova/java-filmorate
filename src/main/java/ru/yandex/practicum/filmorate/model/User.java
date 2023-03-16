package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.With;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    public static int newId = 0;
    @DecimalMin("0")
    private long id;
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();
}
