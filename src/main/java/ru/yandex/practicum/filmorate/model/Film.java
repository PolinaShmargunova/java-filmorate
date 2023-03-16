package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    public static int newId = 0;
    @DecimalMin("0")
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private LocalDate releaseDate;
    @NotBlank
    private Long duration;
    private Set<Long> likes = new HashSet<>();
    @NotBlank
    private int rate;
    @NotBlank
    private Mpa mpa;
    @NotBlank
    @Valid
    private Set<Genre> genres = new HashSet<>();
}
