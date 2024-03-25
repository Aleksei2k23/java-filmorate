package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    private int id;
    private String name;
    private String description;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;
    private int durationInMinutes;
}
