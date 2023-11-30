package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnCategoryStore implements CategoryStore {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query(
                "FROM Category",
                Category.class
        );
    }

    @Override
    public List<Category> findAllById(List<Integer> list) {
        return crudRepository.query(
                "FROM Category c WHERE c.id IN (:fId)",
                Category.class,
                Map.of("fId", list));
    }

    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                "FROM Category c WHERE c.id = :fId",
                Category.class,
                Map.of("fId", id));
    }
}
