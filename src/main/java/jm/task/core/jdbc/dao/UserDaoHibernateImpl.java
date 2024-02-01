package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
         String createUsersTable = "CREATE TABLE IF NOT EXISTS USERS (" +
                "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(25) NOT NULL, " +
                "lastName VARCHAR(25) NOT NULL, " +
                "age TINYINT)";

        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(createUsersTable).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        String dropUsersTable = "DROP TABLE IF EXISTS USERS";

        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(dropUsersTable).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            var user = new User(name, lastName, age);
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }

    }

    @Override
    public void removeUserById(long id) {
        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            var user = session.get(User.class, id);
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (var session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}
