package ru.yandex.practicum.filmorate.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserStorage {
    private static int nextId = 0;
    private List<User> users = new ArrayList<>();

    public boolean add(User user) {
        return users.add(user);
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    public boolean remove(User user) {
        return users.remove(user);
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(u -> u.getId() == id).findAny();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public static int getNextId() {
        return ++nextId;
    }
}
