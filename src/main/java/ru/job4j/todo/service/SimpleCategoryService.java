package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private final CategoryStore hbnCategoryStore;

    @Override
    public Set<Category> findAll() {
        return new HashSet<>(hbnCategoryStore.findAll());
    }

    @Override
    public Set<Category> findAllById(List<Integer> list) {
        return new HashSet<>(hbnCategoryStore.findAllById(list));
    }

    @Override
    public Optional<Category> findById(int id) {
        return hbnCategoryStore.findById(id);
    }
}
