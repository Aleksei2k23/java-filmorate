package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UserController {
    private List<User> users = new ArrayList<>();

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        validateUser(user);
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }

        Optional<User> existingUserOpt =
                users.stream().filter(u -> u.getId() == user.getId()).findAny();
        if (existingUserOpt.isPresent()) {
            users.remove(existingUserOpt.get());
            users.add(user);
            log.info("Обновлён пользователь {}", user);
        } else {
            users.add(user);
            log.info("Добавлен пользователь {}", user);
        }
        return user;
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        validateUser(user);

        if (users.contains(user)) {
            log.error("Пользователь уже существует.");
            throw new ValidationException("Пользователь уже существует.");
        }
        users.add(user);
        return user;
    }

    private void validateUser(User user) {
        try {
            if (user.getEmail().equals("") || !user.getEmail().contains("@")) {
                throw new ValidationException("Email не должен быть пустым и должен содержать символ '@'.");
            }
            if (user.getLogin().equals("") || user.getLogin().contains(" ")) {
                throw new ValidationException("Логин не может быть пустым и не может содержать пробелы.");
            }
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Дата рождения не может быть в будущем.");
            }
        } catch (ValidationException e) {
            log.error("Пользователь не прошёл валидацию: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
