package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> users = new ArrayList<>();

    @PutMapping("/user")
    public User addOrUpdate(@RequestBody User user) {
        if (users.contains(user)) {
            users.remove(user);
        }
        users.add(user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
