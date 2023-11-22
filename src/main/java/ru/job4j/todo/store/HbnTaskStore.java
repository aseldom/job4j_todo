package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnTaskStore implements TaskStore {

    CrudRepository crudRepository;

    @Override
    public Task add(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        int res = crudRepository.runWithConfirm(
                "DELETE FROM Task WHERE id = :fId",
                Map.of("fId", id)
        );
        return res != 0;
    }

    @Override
    public boolean update(Task task) {
        int res = crudRepository.runWithConfirm(
                "UPDATE Task SET title = :fTitle, description = :fDescription, done = :fDone WHERE id = :fId",
                Map.of(
                        "fId", task.getId(),
                        "fTitle", task.getTitle(),
                        "fDescription", task.getDescription(),
                        "fDone", task.isDone())
        );
        return res != 0;
    }

    @Override
    public boolean updateDone(int id) {
        int res = crudRepository.runWithConfirm(
                "UPDATE Task SET done = :fDone WHERE id = :fId",
                Map.of(
                        "fId", id,
                        "fDone", true
                )
        );
        return res != 0;
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                "FROM Task WHERE id = :fId",
                Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query(
                "FROM Task",
                Task.class
        );
    }

    @Override
    public Collection<Task> findCompleteNew(boolean doneTask) {
        return crudRepository.query(
                "FROM Task WHERE done = :fDone",
                Task.class,
                Map.of("fDone", doneTask)
        );
    }

}