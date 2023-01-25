package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidator;


import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmValidator filmValidator;

    @GetMapping
    public Collection<Film> findAllFilms() {
        log.debug("GET-запрос: получить список всех имеющихся фильмов");
        return filmValidator.findAllFilms();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException {
        log.debug("POST-запрос: добавить новый фильм");
        return filmValidator.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        log.debug("PUT-запрос: обновить имеющийся фильм");
        return filmValidator.updateFilm(film);
    }
}
