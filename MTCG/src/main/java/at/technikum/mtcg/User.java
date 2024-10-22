package at.technikum.mtcg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean register() {
        String insertUserSQL = "INSERT INTO users (username, password, coins) VALUES (?, ?, 20)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(insertUserSQL)) {

            stmt.setString(1, this.username);
            stmt.setString(2, this.password); // Später sollten Passwörter verschlüsselt werden!

            stmt.executeUpdate();
            System.out.println("Registrierung erfolgreich!");
            return true;

        } catch (SQLException e) {
            System.out.println("Fehler bei der Registrierung: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        User user = new User("testuser", "testpassword");
        user.register();
    }
}
