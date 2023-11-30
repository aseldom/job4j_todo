package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskStore {

    Task add(Task task);

    boolean deleteById(int id);

    void update(Task task);

    boolean updateDone(int id);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findCompleteNew(boolean doneTask);

}
