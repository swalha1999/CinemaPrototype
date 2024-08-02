package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDAO {

    private final Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public User addUser(User newUser) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(newUser);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return newUser;
    }

    public User deleteUser(User deletedUser) {
        Transaction transaction = null;
        User user = null;
        try {
            transaction = session.beginTransaction();
            user = session.get(User.class, deletedUser.getId());
            if (user != null) {
                session.delete(user);
                session.flush();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public User editUser(User editedUser) {
        Transaction transaction = null;
        User user = null;
        try {
            transaction = session.beginTransaction();
            user = session.get(User.class, editedUser.getId());
            if (user != null) {
                user.setFirstName(editedUser.getFirstName());
                user.setLastName(editedUser.getLastName());
                user.setEmail(editedUser.getEmail());
                user.setPassword(editedUser.getPassword());
                user.setRole(editedUser.getRole());
                session.update(user);
                session.flush();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public User getUserById(int id) {
        Transaction transaction = null;
        User user = null;
        try {
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getUsers() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        return session.createQuery(criteria).getResultList();
    }

    public User getUserbyUsername(String username) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("username"), username));
        List<User> users = session.createQuery(criteria).getResultList();
        return users.isEmpty() ? null : users.getFirst();
    }

    public User blockUserById(int id) {
        Transaction transaction = null;
        User user = null;
        try {
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            if (user != null) {
                user.setBlocked(true);
                session.update(user);
                session.flush();
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    static public String generateSalt(){
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            salt.append((char) (Math.random() * 26 + 'a'));
        }
        return salt.toString();
    }

    static public String hashPassword(String password, String salt) {
        return password + salt;
    }

    static public boolean checkPassword(String password, String salt, String hashedPassword) {
        return hashedPassword.equals(hashPassword(password , salt));
    }





}
