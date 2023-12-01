package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.Collection;

public class ConvertTimeZone {

    public static Collection<Task> covertTimeZone(Collection<Task> tasks, User user) {
        for (Task task : tasks) {
            task.setCreated(task.getCreated()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of(user.getTimeZone()))
                    .toLocalDateTime());
        }
        return tasks;
    }
}
