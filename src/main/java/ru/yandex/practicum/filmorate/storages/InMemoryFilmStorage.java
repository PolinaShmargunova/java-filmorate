package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final List<Film> films = new ArrayList<>();

    @Override
    public List<Film> findAll() {
        return films;
    }

    @Override
    public Film findById(Long id) {
        Film flm = films.stream().filter(film -> film.getId() == id).findFirst().orElse(null);
        if (flm == null) throw new NotFoundException("Film not found");
        return flm;
    }

    @Override
    public Film insert(Film film) {
        film.setId(++Film.newId);
        films.add(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.remove(this.findById(film.getId()));
        films.add(film);
        return film;
    }
}
