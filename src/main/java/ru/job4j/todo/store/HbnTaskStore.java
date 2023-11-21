package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnTaskStore implements TaskStore {

    private final SessionFactory sf;

    @Override
    public Task add(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        int rowCount = 0;
        try {
            session.beginTransaction();
            rowCount = session.createQuery(
                            "DELETE FROM Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rowCount != 0;

    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        int rowCount = 0;
        try {
            session.beginTransaction();
            rowCount = session.createQuery(
                            "UPDATE Task SET title = :fTitle, description = :fDescription, done = :fDone WHERE id = :fId")
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rowCount != 0;
    }

    @Override
    public boolean updateDone(int id) {
        Session session = sf.openSession();
        int rowCount = 0;
        try {
            session.beginTransaction();
            rowCount = session.createQuery(
                            "UPDATE Task SET done = :fDone WHERE id = :fId")
                    .setParameter("fDone", true)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rowCount != 0;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Optional<Task> result;
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task WHERE id = :fId", Task.class)
                    .setParameter("fId", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        List<Task> result;
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task t ORDER BY t.created", Task.class)
                    .list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findNewComplete(boolean newTask) {
        Session session = sf.openSession();
        Collection<Task> result;
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task WHERE done = :fDone", Task.class)
                    .setParameter("fDone", !newTask)
                    .list();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return result;
    }

}