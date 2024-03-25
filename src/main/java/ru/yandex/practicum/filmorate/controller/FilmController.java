package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FilmController {
    private List<Film> films = new ArrayList<>();

    @PutMapping("/film")
    public Film addOrUpdate(@RequestBody Film film) {
        validateFilm(film);

        Optional<Film> existingFilmOpt =
                films.stream().filter(f -> f.getId() == film.getId()).findAny();
        if (existingFilmOpt.isPresent()) {
            films.remove(existingFilmOpt.get());
        }
        films.add(film);
        return film;
    }

    private void validateFilm(Film film) {
        if (film.getName().equals("")) {
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма не может превышать 200 символов.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException("Дата релиза должна быть не ранее 28 декабря 1895 года.");
        }
        if (film.getDurationInMinutes() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть больше нуля.");
        }
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }
}
