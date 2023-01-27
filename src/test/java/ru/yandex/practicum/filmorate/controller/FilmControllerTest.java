package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {
    private FilmController filmController;
    private Film film;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController(new FilmService());
        film = Film.builder()
                .id(1)
                .name("FilmName")
                .description("FilmDescription")
                .releaseDate(LocalDate.of(2020, 10, 12))
                .duration(120)
                .build();
    }

    @Test
    void shouldAddFilm() {
        filmController.addFilm(film);
        assertEquals(1, filmController.findAllFilms().size());
    }

    @Test
    void shouldUpdateFilm() {
        Film film2 = Film.builder()
                .id(1)
                .name("FilmName")
                .description("FilmNewDescription")
                .releaseDate(LocalDate.of(2020, 10, 12))
                .duration(130)
                .build();
        filmController.addFilm(film);
        filmController.updateFilm(film2);
        List<Film> filmList = new ArrayList<>();
        filmList.addAll(filmController.findAllFilms());
        assertEquals("FilmNewDescription", filmList.get(0).getDescription());
    }

    @Test
    void shouldGetAllFilms() {
        Film film2 = Film.builder()
                .id(2)
                .name("NewFilmName")
                .description("Film2Description")
                .releaseDate(LocalDate.of(2020, 10, 12))
                .duration(130)
                .build();
        filmController.addFilm(film2);
        filmController.addFilm(film);
        assertEquals(2, filmController.findAllFilms().size());
    }

    @Test
    void shouldThrowExceptionThenAddEmptyName() {
        Film film2 = Film.builder()
                .id(2)
                .name("")
                .description("Film2Description")
                .releaseDate(LocalDate.of(2020, 10, 12))
                .duration(130)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film2));
        String expectedMessage = "Название фильма не может быть пустым.";
        assertEquals(exception.getMessage(), expectedMessage);
        assertEquals(0, filmController.findAllFilms().size());
    }

    @Test
    void shouldTrowExceptionThenAddTooLongDescription() {
        Film film2 = Film.builder()
                .id(2)
                .name("Film2Name")
                .description("В этом описании более 200 символов, по этой причине создание объекта с полем " +
                        "film.description не пройдет валидацию контроллера. В поле необходимо указать описание " +
                        "фильма, не превышающим количество символов, равное 200.")
                .releaseDate(LocalDate.of(2020, 10, 12))
                .duration(130)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film2));
        String expectedMessage = "Описание фильма не может превышать 200 символов.";
        assertEquals(exception.getMessage(), expectedMessage);
        assertEquals(0, filmController.findAllFilms().size());
    }

    @Test
    void shouldThrowExceptionThenAddTooEarlierDateRelease() {
        Film film2 = Film.builder()
                .id(2)
                .name("Film2Name")
                .description("Film2Description")
                .releaseDate(LocalDate.of(1890, 10, 12))
                .duration(130)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film2));
        String expectedMessage = "Дата релиза должна быть не раньше 28.12.1895 (первый фильм в истории).";
        assertEquals(exception.getMessage(), expectedMessage);
        assertEquals(0, filmController.findAllFilms().size());
    }

    @Test
    void shouldThrowExceptionThenAddNegativeDuration() {
        Film film2 = Film.builder()
                .id(2)
                .name("Film2Name")
                .description("Film2Description")
                .releaseDate(LocalDate.of(1990, 10, 12))
                .duration(-1)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film2));
        String expectedMessage = "Продолжительность фильма не может быть меньше нуля или равняться нулю.";
        assertEquals(exception.getMessage(), expectedMessage);
        assertEquals(0, filmController.findAllFilms().size());
    }
}