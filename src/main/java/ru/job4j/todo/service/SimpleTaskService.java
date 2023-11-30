package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore taskStore;

    @Override
    public Task add(Task task) {
        return taskStore.add(task);
    }

    @Override
    public boolean deleteById(int id) {
        return taskStore.deleteById(id);
    }

    @Override
    public void update(Task task) {
        taskStore.update(task);
    }

    @Override
    public boolean updateDone(int id) {
        return taskStore.updateDone(id);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public Collection<Task> findCompleteNew(boolean doneTask) {
        return taskStore.findCompleteNew(doneTask);
    }

}
