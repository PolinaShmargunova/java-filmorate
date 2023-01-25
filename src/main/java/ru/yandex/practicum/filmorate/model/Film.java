package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Data
@Builder
public class Film {

    public static final LocalDate EARLIEST_DATE_OF_RELEASE = LocalDate.of(1895, 12, 28);
    public static final int MAX_LENGTH_OF_DESCRIPTION = 200;

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;

}
