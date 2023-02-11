package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storages.UserStorage;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public void addFriend(int id, int friendId) {
        User firstFriend = getUserById(id);
        User secondFriend = getUserById(friendId);
        if (firstFriend == null) {
            throw new NotFoundException("Пользователь с id=" + id + " не найден");
        }
        if (secondFriend == null) {
            throw new NotFoundException("Пользователь с id=" + friendId + " не найден");
        }
        Set<Integer> userFriendsIds = firstFriend.getFriendsIds();
        userFriendsIds.add(friendId);
        firstFriend = firstFriend.withFriendsIds(userFriendsIds);
        userFriendsIds = secondFriend.getFriendsIds();
        userFriendsIds.add(id);
        secondFriend = secondFriend.withFriendsIds(userFriendsIds);
        userStorage.updateUser(firstFriend);
        userStorage.updateUser(secondFriend);
    }

    public void removeFriend(int id, int friendId) {
        User firstFriend = getUserById(id);
        User secondFriend = getUserById(friendId);
        if (firstFriend == null) {
            throw new NotFoundException("Пользователь с id=" + id + " не найден");
        }
        if (secondFriend == null) {
            throw new NotFoundException("Пользователь с id=" + friendId + " не найден");
        }
        Set<Integer> userFriendsIds = firstFriend.getFriendsIds();
        userFriendsIds.remove(friendId);
        firstFriend = firstFriend.withFriendsIds(userFriendsIds);
        userFriendsIds = secondFriend.getFriendsIds();
        userFriendsIds.remove(id);
        secondFriend = secondFriend.withFriendsIds(userFriendsIds);
        userStorage.updateUser(firstFriend);
        userStorage.updateUser(secondFriend);
    }

    public List<User> getMutualFriends(int id, int friendId) {
        User firstUser = getUserById(id);
        User secondUser = getUserById(friendId);
        if (firstUser == null || secondUser == null) {
            return List.of();
        }
        Set<Integer> firstUserFriends = firstUser.getFriendsIds();
        Set<Integer> secondUserFriends = secondUser.getFriendsIds();
        firstUserFriends.retainAll(secondUserFriends);
        return firstUserFriends.stream().map(this::getUserById).collect(Collectors.toList());
    }

    public User getUserById(int id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + id + " не найден");
        }
        return user;
    }

    public List<User> getFriends(int id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + id + " не найден");
        }
        return user.getFriendsIds().stream().map(this::getUserById).collect(Collectors.toList());
    }

    public User addUser(User user) {
        if (isUserDataErrors(user)) {
            throw new ValidationException();
        }
        String name = user.getName();
        if (StringUtils.isBlank(name)) {
            name = user.getLogin();
        }
        user = user.withName(name);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()) == null) {
            throw new NotFoundException("Пользователь с id=" + user.getId() + " не найден");
        }
        if (isUserDataErrors(user)) {
            throw new ValidationException();
        }
        String name = user.getName();
        if (StringUtils.isBlank(name)) {
            name = user.getLogin();
        }
        user = user.withName(name);
        return userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    private boolean isUserDataErrors(User user) {
        boolean isLoginErrors = Boolean.parseBoolean(user.getLogin());
        LocalDate birthday = LocalDate.parse(user.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean isBirthdayErrors = birthday.isAfter(LocalDate.now());
        return isLoginErrors || isBirthdayErrors;
    }
}