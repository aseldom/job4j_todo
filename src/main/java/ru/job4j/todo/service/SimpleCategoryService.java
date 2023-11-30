package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    CategoryStore hbnCategoryStore;

    @Override
    public Collection<Category> findAll() {
        return hbnCategoryStore.findAll();
    }

    @Override
    public List<Category> findAllById(List<Integer> list) {
        return hbnCategoryStore.findAllById(list);
    }

    @Override
    public Optional<Category> findById(int id) {
        return hbnCategoryStore.findById(id);
    }
}
