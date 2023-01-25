package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.filmorate.validation.FilmValidator.TOO_LONG_DESCRIPTION;

@SpringBootTest
class FilmControllerTest {
    private FilmController filmController;
    private Film film;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController(new FilmValidator(new HashMap<>()));
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

        ValidationException validationException = assertThrows(ValidationException.class, () -> filmController.addFilm(film2));
        assertEquals(validationException.getMessage(), validationException.getMessage());
        assertEquals(0, filmController.findAllFilms().size());
    }

    @Test
    void shouldTrowExceptionThenAddTooLongDescription() {
        Film film2 = Film.builder()
                .id(2)
                .name("Film2Name")
                .description("В этом описании более 200 символов. " +
                        "Объект с полем film.description не пройдет валидацию контроллера. В поле необходимо указать описание " +
                        " фильма, не превышающим количество символов, равное 200. В противном случае описание невозможно сохранить")
                .releaseDate(LocalDate.of(2020, 10, 12))
                .duration(130)
                .build();

        ValidationException validationException = assertThrows(ValidationException.class, () -> filmController.addFilm(film2));
        assertEquals(validationException.getMessage(), validationException.getMessage());
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
        assertEquals(exception.getMessage(), exception.getMessage());
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
        assertEquals(exception.getMessage(), exception.getMessage());
        assertEquals(0, filmController.findAllFilms().size());
    }
}