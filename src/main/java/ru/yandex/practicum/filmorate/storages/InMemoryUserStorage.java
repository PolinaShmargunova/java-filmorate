package ru.yandex.practicum.filmorate.storages;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final List<User> users = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(Long id) {
        User usr = users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
        if (usr == null) throw new NotFoundException("User not found");
        return usr;
    }

    @Override
    public User insert(User user) {
        user.setId(++User.newId);
        users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        users.remove(findById(user.getId()));
        users.add(user);
        return user;
    }
}
