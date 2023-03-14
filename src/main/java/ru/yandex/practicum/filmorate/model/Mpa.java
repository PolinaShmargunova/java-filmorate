package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Mpa {
    @NotBlank
    private long id;
    @NotBlank
    private String name;
}
