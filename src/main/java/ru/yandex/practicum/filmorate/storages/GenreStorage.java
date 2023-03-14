package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    List<Genre> findAll();

    List<Genre> findByFilm(long filmId);

    Genre findById(Long id);

    Genre insert(Genre film);

    Genre update(Genre film);
}
