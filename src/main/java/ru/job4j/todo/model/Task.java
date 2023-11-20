package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private boolean done;
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
}
