package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryService {

    Set<Category> findAll();

    Set<Category> findAllById(List<Integer> list);

    Optional<Category> findById(int id);

}
