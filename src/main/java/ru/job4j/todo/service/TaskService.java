package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    Task add(Task task);

    boolean deleteById(int id);

    boolean update(Task task);

    boolean updateDone(int id);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findNew();

    Collection<Task> findComplete();

}
