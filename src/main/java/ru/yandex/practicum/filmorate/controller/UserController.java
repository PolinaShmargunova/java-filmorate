package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.validation.UserValidator;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserValidator userValidator;

    @GetMapping
    public Collection<User> findAllUsers() {
        log.debug("GET-запрос: получить список всех зарегистрированных пользователей.");
        return userValidator.findAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        log.debug("POST-запрос: создать нового пользователя.");
        return userValidator.createUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException {
        log.debug("PUT-запрос: обновить данные зарегистрированного пользователя.");
        return userValidator.updateUser(user);
    }
}
