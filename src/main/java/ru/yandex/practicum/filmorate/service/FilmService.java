package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storages.FilmStorage;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FilmService {
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private final FilmStorage storage;
    private final UserStorage userStorage;

    public List<Film> getAll() {
        return storage.findAll();
    }

    public Film getById(Long id) {
        return storage.findById(id);
    }

    public Film add(Film film) {
        this.validate(film);
        return storage.insert(film);
    }

    public Film update(Film film) {
        this.validate(film);
        return storage.update(film);
    }

    public void addLike(Long id, Long userId) {
        Film film = getById(id);
        userStorage.findById(userId);
        film.getLikes().add(userId);
        update(film);
    }

    public void removeLike(Long id, Long userId) {
        Film film = getById(id);
        userStorage.findById(userId);
        film.getLikes().remove(userId);
        update(film);
    }

    public List<Film> getTopNFilms(Integer count) {
        List<Film> films = this.getAll();
        if (films.isEmpty()) return films;
        Collections.reverse(films);
        return films.subList(0, Math.min(count, films.size()));
    }

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.error("Name не может быть пустым");
            throw new ValidationException("Name не может быть пустым");
        }
        if (film.getDescription() != null && film.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            log.error("Максимальная длина описания — " + MAX_DESCRIPTION_LENGTH + " символов");
            throw new ValidationException("Максимальная длина описания — " + MAX_DESCRIPTION_LENGTH + " символов");
        }
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.error("Дата релиза должна быть не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getGenres() == null) {
            log.error("Список жанров не может быть пустым");
            throw new ValidationException("Список жанров не может быть пустым");
        }

        if (film.getDuration() != null && film.getDuration() < 0) {
            log.error("Продолжительность фильма должна быть положительной");
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
    }
}
