package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();

        userDao.saveUser("Иван", "Иванов", (byte) 30);
        userDao.saveUser("Петр", "Петров", (byte) 18);
        userDao.saveUser("Сидор", "Сидоров", (byte) 20);
        userDao.saveUser("Антон", "Антонов", (byte) 25);

        userDao.removeUserById(3);
        var users = userDao.getAllUsers();
        System.out.println(users);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
