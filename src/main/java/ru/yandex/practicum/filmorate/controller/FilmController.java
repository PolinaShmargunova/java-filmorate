package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getAll() {
        log.info("/films get all");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable Long id) {
        log.info("/films get by id");
        return filmService.getById(id);
    }

    @PostMapping
    public Film post( @RequestBody Film film) {
        log.info("/films post");
        return filmService.add(film);
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        log.info("/films patch");
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void like(@PathVariable Long id, @PathVariable Long userId) {
        log.info("/films like");
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("/films remove like");
        filmService.removeLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> getTop(@RequestParam(defaultValue = "10") Integer count) {
        log.info("/films get top");
        return filmService.getTopNFilms(count);
    }
}
