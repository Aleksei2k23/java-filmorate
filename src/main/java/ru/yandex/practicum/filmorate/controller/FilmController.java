package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class FilmController {
    private static int nextId = 0;
    private List<Film> films = new ArrayList<>();

    @PutMapping("/films")
    public Film update(@RequestBody Film film) {
        validateFilm(film);

        Optional<Film> existingFilmOpt =
                films.stream().filter(f -> f.getId() == film.getId()).findAny();
        if (existingFilmOpt.isPresent()) {
            films.remove(existingFilmOpt.get());
            films.add(film);
            log.info("Обновлён фильм {}", film);
        } else {
            log.error("Фильм уже существует.");
            throw new ValidationException("Фильм не существует.");
        }
        return film;
    }

    @PostMapping("/films")
    public Film create(@RequestBody Film film) {
        validateFilm(film);
        film.setId(++nextId);

        if (films.contains(film)) {
            log.error("Фильм уже существует.");
            throw new ValidationException("Фильм уже существует.");
        }
        films.add(film);
        return film;
    }

    private void validateFilm(Film film) {
        try {
            if (film.getName().isEmpty()) {
                throw new ValidationException("Название фильма не может быть пустым.");
            }
            if (film.getDescription().length() > 200) {
                throw new ValidationException("Описание фильма не может превышать 200 символов.");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
                throw new ValidationException("Дата релиза должна быть не ранее 28 декабря 1895 года.");
            }
            if (film.getDuration() <= 0) {
                throw new ValidationException("Продолжительность фильма должна быть больше нуля.");
            }
        } catch (ValidationException e) {
            log.error("Фильм не прошёл валидацию: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }
}
