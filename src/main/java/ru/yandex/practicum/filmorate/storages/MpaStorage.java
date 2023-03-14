package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    List<Mpa> findAll();

    Mpa findById(Long id);

    Mpa insert(Mpa film);

    Mpa update(Mpa film);
}
