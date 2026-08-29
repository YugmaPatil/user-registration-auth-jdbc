package main;

import java.util.Scanner;

import model.User;
import service.UserService;
import util.Session;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserService service = new UserService();
        while (true) {

            System.out.println("\n------------- User Auth System -----------");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter Choice : ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // ================= REGISTER =================
                case 1:
                    System.out.print("Enter username : ");
                    String username = sc.nextLine();

                    System.out.print("Enter email : ");
                    String email = sc.nextLine();

                    System.out.print("Enter password : ");
                    String password = sc.nextLine();

                    System.out.print("Enter role (ADMIN/USER) : ");
                    String role = sc.nextLine();

                    if (service.isEmailExists(email)) {
                        System.out.println("User already exists ❌");
                    } else {
                        boolean result = service.registerUser(
                                new User(username, email, password, role)
                        );

                        if (result) {
                            System.out.println("Registration Successful ✅");
                        } else {
                            System.out.println("Registration Failed ❌");
                        }
                    }
                    break;

                // ================= LOGIN =================
                case 2:
                    System.out.print("Enter email : ");
                    String loginEmail = sc.nextLine();

                    System.out.print("Enter password : ");
                    String loginPassword = sc.nextLine();

                    User loggedInUser = service.loginUser(loginEmail, loginPassword);

                    if (loggedInUser != null) {
                        Session.setUser(loggedInUser);
                        System.out.println("Login Successful ✅");

                        if (loggedInUser.getRole().equalsIgnoreCase("ADMIN")) {
                            System.out.println("Welcome Admin 👑");
                            adminMenu(sc);
                        } else {
                            System.out.println("Welcome User 👤");
                            userMenu(sc, loggedInUser);
                        }

                    } else {
                        System.out.println("Invalid Credentials ❌");
                    }
                    break;

                // ================= EXIT =================
                case 3:
                    System.out.println("Exiting... 👋");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice ❌");
            }
        }
    }

    // ================= ADMIN MENU =================
    public static void adminMenu(Scanner sc) {

        UserService service = new UserService();
        while (true) {
            System.out.println("\n👑 Admin Menu");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. Logout");
            System.out.print("Enter choice : ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    service.viewAllUsers();
                    break;

                case 2:
                    System.out.print("Enter email to delete : ");
                    String email = sc.nextLine();

                    boolean deleted = service.deleteUser(email);

                    if (deleted) {
                        System.out.println("User deleted successfully 🗑");
                    } else {
                        System.out.println("User not found ❌");
                    }
                    break;

                case 3:
                    System.out.println("Logging out... 🏃‍♂️");
                    Session.logout();
                    return;

                default:
                    System.out.println("Invalid choice ❌");
            }
        }
    }

    // ================= USER MENU =================
    public static void userMenu(Scanner sc, User loggedInUser) {

        UserService service = new UserService();

        while (true) {
            System.out.println("\n👤 User Menu");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Delete Account");
            System.out.println("4. Logout");
            System.out.print("Enter choice : ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                // ===== VIEW PROFILE =====
            case 1:
                User loggedInUsers = Session.getUser();
                if(loggedInUsers != null) {
                    System.out.println("\n----- Profile -----");
                    System.out.println("Username : " + loggedInUsers.getUsername());
                    System.out.println("Email    : " + loggedInUsers.getEmail());
                    System.out.println("Role     : " + loggedInUsers.getRole());
                } else {
                    System.out.println("No user is currently logged in.");
                }
                    break;

                // ===== UPDATE PROFILE =====
                case 2:
                    System.out.print("Enter new username : ");
                    String newUsername = sc.nextLine();

                    System.out.print("Enter new password : ");
                    String newPassword = sc.nextLine();

                    boolean updated = service.updateUser(
                            loggedInUser.getEmail(),
                            newUsername,
                            newPassword
                    );

                    if (updated) {
                        System.out.println("Profile updated successfully 😊");

                        // refresh user data
                        loggedInUser = service.getUserByEmail(loggedInUser.getEmail());
                        Session.setUser(loggedInUser);  

                    } else {
                        System.out.println("Update failed ❌");
                    }
                    break;

                // ===== DELETE ACCOUNT =====
                case 3:
                    System.out.print("Are you sure? (yes/no): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("yes")) {

                        boolean deleted = service.deleteOwnAccount(loggedInUser.getEmail());

                        if (deleted) {
                            System.out.println("Account deleted successfully 🗑");
                            Session.logout();
                            return;
                        } else {
                            System.out.println("Deletion failed ❌");
                        }

                    } else {
                        System.out.println("Cancelled 👍");
                    }
                    break;

                // ===== LOGOUT =====
                case 4:
                    System.out.println("Logging out... 🏃‍♂️");
                    Session.setUser(null);
                    return;

                default:
                    System.out.println("Invalid choice ❌");
            }
        }
    }
}