package ru.yandex.practicum.filmorate.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmValidator {

    public static final LocalDate EARLIEST_DATE_OF_RELEASE = LocalDate.of(1895, 12, 28);
    public static final int MAX_LENGTH_OF_DESCRIPTION = 200;

    public static final String TOO_LONG_DESCRIPTION = "В этом описании более 200 символов. " +
            "объекта с полем film.description не пройдет валидацию контроллера. В поле необходимо указать описание " +
            "фильма, не превышающим количество символов, равное 200.";
    private final Map<Integer, Film> films;
    private static final AtomicInteger id = new AtomicInteger(0);

    public Film addFilm(Film film) throws ValidationException {
        validateFilm(film);
        film.setId(id.incrementAndGet());
        films.put(film.getId(), film);
        log.debug("Успешно добавлен новый фильм c id=" + film.getId());
        return film;
    }

    public Collection<Film> findAllFilms() {
        log.debug("Успешно возвращена коллекция фильмов.");
        return films.values();
    }

    public Film updateFilm(Film film) throws RuntimeException, ValidationException {
        validateFilm(film);
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильм с id=" + film.getId() + " не найден.");
        }
        films.remove(film.getId());
        films.put(film.getId(), film);
        log.debug("Фильм с id=" + film.getId() + " успешно обновлен.");
        return film;
    }

    private Film validateFilm(Film film) throws ValidationException {
        if (film.getName().isBlank() || film.getName().isEmpty()) {
            log.info("Название фильма пустое или поле film.name пустое.");
            throw new ValidationException("Название фильма не может быть пустым.");
        } else if (film.getDescription().length() > MAX_LENGTH_OF_DESCRIPTION) {
            log.info("Кол-во символов в описании фильма превысило максимально допустимое.");
            throw new ValidationException("Описание фильма не может превышать 200 символов.");
        } else if (film.getReleaseDate().isBefore(EARLIEST_DATE_OF_RELEASE)) {
            log.info("Дата релиза фильма ранее 28.12.1895.");
            throw new ValidationException("Дата релиза должна быть не раньше 28.12.1895 (первый фильм в истории).");
        } else if (film.getDuration() <= 0) {
            log.info("Продолжительность фильма меньше нуля.");
            throw new ValidationException("Продолжительность фильма не может быть меньше нуля или равняться нулю.");
        }
        return film;
    }
}
