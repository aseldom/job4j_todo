package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.TimeZone;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final UserStore userRepository;

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @Override
    public Collection<TimeZone> findAllTimeZone() {
        return Arrays.stream(TimeZone.getAvailableIDs()).map(TimeZone::getTimeZone).toList();
    }
}
