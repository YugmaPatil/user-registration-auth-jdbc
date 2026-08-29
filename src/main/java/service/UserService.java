package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO dao = new UserDAO();

    // ================= REGISTER =================
    public boolean registerUser(User user) {

        if (user == null) {
            return false;
        }

        if (user.getUsername() == null ||
            user.getUsername().trim().isEmpty()) {
            return false;
        }

        if (user.getEmail() == null ||
            user.getEmail().trim().isEmpty()) {
            return false;
        }

        if (user.getPassword() == null ||
            user.getPassword().trim().isEmpty()) {
            return false;
        }

        if (user.getRole() == null ||
            (!user.getRole().equalsIgnoreCase("ADMIN")
             && !user.getRole().equalsIgnoreCase("USER"))) {
            return false;
        }

        if (dao.isEmailExists(user.getEmail())) {
            return false;
        }

        return dao.registerUser(user);
    }
    public boolean isEmailExists(String email) {
    if (email == null || email.trim().isEmpty()) {
        return false;
    }

    return dao.isEmailExists(email);
}

    // ================= LOGIN =================
    public User loginUser(String email, String password) {

        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            return null;
        }

        return dao.loginUser(email, password);
    }

    // ================= GET USER =================
    public User getUserByEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        return dao.getUserByEmail(email);
    }

    // ================= UPDATE USER =================
    public boolean updateUser(
            String email,
            String username,
            String password) {

        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        return dao.updateUser(email, username, password);
    }

    // ================= DELETE USER =================
    public boolean deleteUser(String email) {

        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        return dao.deleteUser(email);
    }

    // ================= DELETE OWN ACCOUNT =================
    public boolean deleteOwnAccount(String email) {

        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        return dao.deleteOwnAccount(email);
    }

    // ================= VIEW ALL USERS =================
    public void viewAllUsers() {
        dao.viewAllUsers();
    }
}