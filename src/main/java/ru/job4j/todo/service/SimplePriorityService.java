package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.PriorityStore;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimplePriorityService implements PriorityService {

    private final PriorityStore hbnPriorityStore;

    @Override
    public Collection<Priority> findAll() {
        return hbnPriorityStore.findAll();
    }

    @Override
    public Optional<Priority> findById(int id) {
        return hbnPriorityStore.findById(id);
    }
}
