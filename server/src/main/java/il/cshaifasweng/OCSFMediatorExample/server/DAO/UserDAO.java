package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageType;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.MessageVersion;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public class UserDAO {

    private Session session;

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
            return null;
        }
        return newUser;
    }

    public User deleteUser(User deletedUser) {
        // TODO: fix this is not right and doesn't indicate if the user was deleted or not out side of the function
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
                user.setHashedPassword(editedUser.getHashedPassword());
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

    private User updateUser(User updatedUser) {
        Transaction transaction = null;
        User user = null;
        try {
            transaction = session.beginTransaction();
            session.update(updatedUser);
            session.flush();
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

    public User getUserbyUsername(String username) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(builder.equal(root.get("username"), username));
        List<User> users = session.createQuery(criteria).getResultList();
        return users.isEmpty() ? null : users.getFirst();
    }

    public List<User> getUsers() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        return session.createQuery(criteria).getResultList();
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

    public RegisterResponse registerUser(RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();

        //check if the user is already registered
        if (getUserbyUsername(registerRequest.getUsername()) != null){
            registerResponse
                    .setSuccess(false)
                    .setMessage("User already registered");
            return registerResponse;
        }

        User user = new User();
        user
            .setUsername(registerRequest.getUsername())
            .setEmail(registerRequest.getEmail())
            .setFirstName(registerRequest.getFirstName())
            .setLastName(registerRequest.getLastName())
            .setSalt(UserDAO.generateSalt())
            .setHashedPassword(UserDAO.hashPassword(registerRequest.getPassword(),user.getSalt()));

        User registerdUser = this.addUser(user);
        if (registerdUser == null){
            registerResponse
                    .setSuccess(false)
                    .setMessage("Error registering user Try again later");

            return registerResponse;
        }

        registerResponse
                .setSuccess(true)
                .setMessage("User registered successfully")
                .setUserId(registerdUser.getId());

        return registerResponse;
    }

    public LoginResponse loginUser(LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();
        // set the default response to false, so we can return it if the login fails without changing it
        loginResponse
                .setSuccess(false)
                .setMessage("password or username is incorrect");

        User user = getUserbyUsername(loginRequest.getUsername());
        if (user == null){
            return loginResponse;
        }

        if (!UserDAO.checkPassword(loginRequest.getPassword(),user)){
            return loginResponse;
        }

        if (user.isBlocked()){
            return loginResponse
                    .setSuccess(false)
                    .setMessage("User is blocked");
        }

        // mark the user as logged in
        user.setLoggedIn();
        this.updateUser(user);

        loginResponse
                .setSuccess(true)
                .setMessage("Login successful")
                .setRole(user.getRole())
                .setUsername(user.getUsername())
                .setUserId(user.getId())
                .setSessionKey(UserDAO.generateSalt()); //TODO: generate a unique session ID and save it to the user database

        return loginResponse;
    }

    public LogoutResponse logoutUser(LogoutRequest logoutRequest) {
        LogoutResponse logoutResponse = new LogoutResponse();

        // we always return true because we don't want to give the user any information about the state of the server
        logoutResponse
                .setSuccess(true)
                .setMessage("Logout successful");

        User user = getUserbyUsername(logoutRequest.getUsername());
        if (user == null) {
            return logoutResponse;
        }

        user.setLoggedOut();
        this.updateUser(user);

        return logoutResponse;
    }

    public Message getAllUsers(Message request) {
        Message response = new Message(MessageType.GET_ALL_USERS_RESPONSE, MessageVersion.V3);
        List<User> users = getUsers();

        if (users.isEmpty()){
            response
                    .setSuccess(false)
                    .setMessage("No users found");
            return response;
        }

        response
                .setSuccess(true)
                .setMessage("Users found")
                .setDataObject(users);

        return response;
    }

    public Message blockUser(Message request) {
        Message response = new Message(MessageType.BLOCK_USER_RESPONSE);

        User userToBlockData = (User) request.getDataObject();

        if (userToBlockData.getUsername().equals("admin")){
            response
                    .setSuccess(false)
                    .setMessage("Can't block admin");
            return response;
        }

        User user = getUserbyUsername(userToBlockData.getUsername());

        if (user == null){
            response
                    .setSuccess(false)
                    .setMessage("User not found");
            return response;
        }

        user.setBlocked(true);
        this.updateUser(user);

        response
                .setSuccess(true)
                .setMessage("User blocked successfully")
                .setDataObject(user);

        return response;
    }

    public Message unblockUser(Message request) {
        Message response = new Message(MessageType.UNBLOCK_USER_RESPONSE);
        User userToUnblockData = (User) request.getDataObject();

        User user = getUserbyUsername(userToUnblockData.getUsername());

        if (user == null){
            return response
                    .setSuccess(false)
                    .setMessage("User not found");
        }

        user.setBlocked(false);
        this.updateUser(user);

        return response
                .setSuccess(true)
                .setMessage("User unblocked successfully")
                .setDataObject(user);
    }

    public Message removeUser(Message request) {
        Message response = new Message(MessageType.REMOVE_USER_RESPONSE);
        User userToRemoveData = (User) request.getDataObject();

        if (userToRemoveData.getUsername().equals("admin")){
            return response
                    .setSuccess(false)
                    .setMessage("Can't remove admin");
        }

        User user = getUserbyUsername(userToRemoveData.getUsername());

        if (user == null){
            return response
                    .setSuccess(false)
                    .setMessage("User not found");
        }

        //TODO: make this inline this is error prone
        deleteUser(user);

        return response
                .setSuccess(true)
                .setMessage("User "+ user.getUsername() +" removed successfully")
                .setDataObject(user); // the user object is returned so the client can update the UI
    }


    public Message changeUserRole(Message request) {
        Message response = new Message(MessageType.CHANGE_USER_ROLE_RESPONSE);
        User userToChangeRoleData = (User) request.getDataObject();

        User user = getUserbyUsername(userToChangeRoleData.getUsername());

        if (user == null){
            return response
                    .setSuccess(false)
                    .setMessage("User not found");
        }

        if (user.getUsername() == "admin"){
            return response
                    .setSuccess(false)
                    .setMessage("Can't change admin role");
        }

        user.setRole(userToChangeRoleData.getRole());
        this.updateUser(user);

        return response
                .setSuccess(true)
                .setMessage("User role changed successfully")
                .setDataObject(user);
    }

    static public String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    static public String hashPassword(String password, String salt) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Add the salt to the digest
            md.update(salt.getBytes());
            // Hash the password
            byte[] hashedPassword = md.digest(password.getBytes());
            // Convert the byte array to a Base64-encoded string
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    static public boolean checkPassword(String password, User user) {
        return user.getHashedPassword().equals(hashPassword(password , user.getSalt()));
    }

    public void setSession(Session session) {
        this.session = session;
    }
}