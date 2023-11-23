package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnUserStore implements UserStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(HbnUserStore.class);

    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
        } catch (Exception e) {
            LOGGER.error("Error occurred", e);
            if (e.getMessage().contains("could not execute statement")) {
                return Optional.empty();
            }
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                "FROM User WHERE login = :fLogin AND password = :fPassword",
                User.class,
                Map.of("fLogin", login, "fPassword", password));
    }
}
