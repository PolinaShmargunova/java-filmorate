package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        super("Передан запрос с некорректными данными");
    }
}