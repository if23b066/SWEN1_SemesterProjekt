package at.technikum.mtcg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private final String Username;
    private final String Password;

    public User(String username, String password) {
        this.Username = username;
        this.Password = password;
    }

    // Registrierungs-Methode (bereits implementiert)
    public boolean register() {
        String insertUserSQL = "INSERT INTO users (username, password, coins) VALUES (?, ?, 20)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(insertUserSQL)) {

            stmt.setString(1, this.Username);
            stmt.setString(2, this.Password); // Später Passwörter verschlüsseln

            stmt.executeUpdate();
            System.out.println("Registrierung erfolgreich!");
            return true;

        } catch (SQLException e) {
            System.out.println("Fehler bei der Registrierung: " + e.getMessage());
            return false;
        }
    }

    // Login-Methode
    public boolean login() {
        String selectUserSQL = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(selectUserSQL)) {

            stmt.setString(1, this.Username);
            stmt.setString(2, this.Password);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("Login erfolgreich!");
                return true;
            } else {
                System.out.println("Login fehlgeschlagen: Falscher Benutzername oder Passwort.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Login: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        // Beispiel für die Registrierung
        User newUser = new User("testuser", "testpassword");
        newUser.register();

        // Beispiel für den Login
        User loginUser = new User("testuser", "testpassword");
        loginUser.login();
    }
}
