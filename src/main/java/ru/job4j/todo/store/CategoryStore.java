package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CategoryStore {

    Collection<Category> findAll();

    List<Category> findAllById(List<Integer> list);

    Optional<Category> findById(int id);

}