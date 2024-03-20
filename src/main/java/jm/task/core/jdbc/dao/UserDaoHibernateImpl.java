package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = Util.getSessionFactory().openSession();

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String createUTSQL = "CREATE TABLE IF NOT EXISTS users" +
                " (Id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(45), LastName VARCHAR(45),Age INT)";

        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(createUTSQL).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String dropUTSQL = "DROP TABLE IF EXISTS users";

        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(dropUTSQL).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);

        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try {
            User user = session.get(User.class, id);
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            users = session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String cleanUTSQL = "DELETE FROM users";

        try {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(cleanUTSQL).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
