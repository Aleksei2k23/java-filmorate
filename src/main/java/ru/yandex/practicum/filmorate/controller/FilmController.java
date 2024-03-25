package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilmController {
    private List<Film> films = new ArrayList<>();

    @PutMapping("/film")
    public Film addOrUpdate(@RequestBody Film film) {
        if (films.contains(film)) {
            films.remove(film);
        }
        films.add(film);
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }
}
