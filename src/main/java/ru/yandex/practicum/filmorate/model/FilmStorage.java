package ru.yandex.practicum.filmorate.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilmStorage {
    private static int nextId = 0;
    private List<Film> films = new ArrayList<>();

    public boolean add(Film film) {
        return films.add(film);
    }

    public boolean contains(Film film) {
        return films.contains(film);
    }

    public boolean remove(Film film) {
        return films.remove(film);
    }

    public Optional<Film> getFilmById(int id) {
        return films.stream().filter(f -> f.getId() == id).findAny();
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(films);
    }

    public static int getNextId() {
        return ++nextId;
    }
}
