package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private static final String SPACE = " ";
    private static final String AT = "@";

    private final UserStorage storage;

    public List<User> getAll() {
        return storage.findAll();
    }

    public User getById(Long id) {
        return storage.findById(id);
    }

    public User add(User user) {
        this.validate(user);
        return storage.insert(user);
    }

    public User update(User user) {
        this.validate(user);
        return storage.update(user);
    }

    public void addToFriends(Long id, Long friendId) {
        User user = getById(id);
        User friend = getById(friendId);
        user.getFriends().add(friendId);
        update(user);
    }

    public void removeFromFriends(Long id, Long friendId) {
        User user = getById(id);
        User friend = getById(friendId);
        user.getFriends().remove(friendId);
        update(user);
    }

    public List<User> getFriends(Long id) {
        return this.getById(id).getFriends().stream().map(this::getById).collect(Collectors.toList());
    }

    public Set<User> findCommonFriends(Long id, Long otherId) {
        Set<Long> intersection = new HashSet<>(this.getById(id).getFriends());
        intersection.retainAll(this.getById(otherId).getFriends());
        return intersection.stream().map(this::getById).collect(Collectors.toSet());
    }

    private void validate(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.error("Email не может быть пустым");
            throw new ValidationException("Email не может быть пустым");
        }
        if (!user.getEmail().contains(AT)) {
            log.error("Email должен содержать символ @");
            throw new ValidationException("Email должен содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            log.error("Login не может быть пустым");
            throw new ValidationException("Login не может быть пустым");
        }
        if (user.getLogin().contains(SPACE)) {
            log.error("Login не может содержать пробелы");
            throw new ValidationException("Login не может содержать пробелы");
        }
        if (user.getName() == null || user.getName().isEmpty()) user.setName(user.getLogin());
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Birthday не может быть в будущем");
            throw new ValidationException("Birthday не может быть в будущем");
        }
    }
}
