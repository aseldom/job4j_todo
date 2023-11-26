package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnPriorityStore implements PriorityStore {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        return crudRepository.query(
                "FROM Priority",
                Priority.class
        );
    }

    @Override
    public Optional<Priority> findById(int id) {
        return crudRepository.optional(
                "FROM Priority p WHERE p.id = :fId",
                Priority.class,
                Map.of("fId", id));
    }

}
