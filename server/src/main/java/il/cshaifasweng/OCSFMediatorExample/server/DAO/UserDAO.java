package il.cshaifasweng.OCSFMediatorExample.server.DAO;

import il.cshaifasweng.OCSFMediatorExample.entities.dataTypes.User;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.requests.*;
import il.cshaifasweng.OCSFMediatorExample.entities.messages.responses.*;
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

        // TODO: check if the user is already logged in and return an error if they are
        // TODO: check the user if they are blocked and return an error if they are

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

    public GetAllUsersResponse getAllUsers(GetAllUsersRequest getAllUsersRequset) {
        GetAllUsersResponse getAllUsersResponse = new GetAllUsersResponse();
        List<User> users = getUsers();
        if (users.isEmpty()){
            getAllUsersResponse
                    .setSucceed(false)
                    .setMessage("No users found");
            return getAllUsersResponse;
        }

        getAllUsersResponse
                .setSucceed(true)
                .setMessage("Users found")
                .setUsers(users);

        return getAllUsersResponse;
    }

    public BlockUserResponse blockUser(BlockUserRequest blockUserRequest) {
        User user = null;
        BlockUserResponse blockUserResponse = new BlockUserResponse();

        if (blockUserRequest.getUserIdToBlock() != 0){
            user = getUserById(blockUserRequest.getUserIdToBlock());
        }else if(!blockUserRequest.getUsernameToBlock().equals("admin")){
            user = getUserbyUsername(blockUserRequest.getUsernameToBlock());
        }

        if (user == null){
            blockUserResponse
                    .setSuccess(false)
                    .setMessage("User not found");
            return blockUserResponse;
        }

        user.setBlocked(true);
        this.updateUser(user);

        blockUserResponse
                .setSuccess(true)
                .setMessage("User blocked successfully");

        return blockUserResponse;
    }

    public UnblockUserResponse unblockUser(UnblockUserRequest unblockUserRequest) {
        User user = null;
        UnblockUserResponse unblockUserResponse = new UnblockUserResponse();

        if (unblockUserRequest.getUserIdToUnblock() != 0){
            user = getUserById(unblockUserRequest.getUserIdToUnblock());
        }else if(!unblockUserRequest.getUsernameToUnblock().equals("admin")){
            user = getUserbyUsername(unblockUserRequest.getUsernameToUnblock());
        }

        if (user == null){
            unblockUserResponse
                    .setSuccess(false)
                    .setMessage("User not found");
            return unblockUserResponse;
        }

        user.setBlocked(false);
        this.updateUser(user);

        unblockUserResponse
                .setSuccess(true)
                .setMessage("User unblocked successfully");

        return unblockUserResponse;
    }

    public RemoveUserResponse removeUser(RemoveUserRequest removeUserRequest) {
        User user = null;
        RemoveUserResponse removeUserResponse = new RemoveUserResponse();

        if (removeUserRequest.getUserId() != 0){
            user = getUserById(removeUserRequest.getUserId());
        }else if(!removeUserRequest.getUsername().equals("admin")){
            user = getUserbyUsername(removeUserRequest.getUsername());
        }

        if (user == null){
            removeUserResponse
                    .setSuccess(false)
                    .setMessage("User not found");
            return removeUserResponse;
        }

        deleteUser(user);

        removeUserResponse
                .setSuccess(true)
                .setMessage("User removed successfully");

        return removeUserResponse;
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

    static public boolean checkPassword(String password, User user) {
        return user.getHashedPassword().equals(hashPassword(password , user.getSalt()));
    }

}