package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Data
@Builder
public class User {

    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
