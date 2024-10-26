package at.technikum.mtcg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {
    private final String Username;
    private final String Password;
    private String token;

    public User(String username, String password) {
        this.Username = username;
        this.Password = password;
    }

    // Methode zur Registrierung des Benutzers (wie zuvor)
    public boolean register() {
        String insertUserSQL = "INSERT INTO users (username, password, coins) VALUES (?, ?, 20)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(insertUserSQL)) {
            stmt.setString(1, this.Username);
            stmt.setString(2, this.Password); // Passwortverschlüsselung später hinzufügen
            stmt.executeUpdate();
            System.out.println("Registrierung erfolgreich!");
            return true;
        } catch (SQLException e) {
            System.out.println("Fehler bei der Registrierung: " + e.getMessage());
            return false;
        }
    }

    // Methode zum Login mit Token-Generierung
    public boolean login() {
        String selectUserSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(selectUserSQL)) {
            stmt.setString(1, this.Username);
            stmt.setString(2, this.Password);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                this.token = generateToken();
                saveTokenInDatabase();
                System.out.println("Login erfolgreich mit Token: " + this.token);
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

    // Token-Generierungsmethode
    private String generateToken() {
        return this.Username + "-" + UUID.randomUUID().toString();
    }

    // Speichere Token in der Datenbank
    private void saveTokenInDatabase() {
        String updateTokenSQL = "UPDATE users SET token = ? WHERE username = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(updateTokenSQL)) {
            stmt.setString(1, this.token);
            stmt.setString(2, this.Username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Fehler beim Speichern des Tokens: " + e.getMessage());
        }
    }

    // Token-Validierungsmethode
    public boolean validateToken(String token) {
        String query = "SELECT * FROM users WHERE username = ? AND token = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.Username);
            stmt.setString(2, token);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Fehler bei der Token-Überprüfung: " + e.getMessage());
            return false;
        }
    }

    // Getter für Username und Token
    public String getUsername() {
        return this.Username;
    }

    public String getToken() {
        return this.token;
    }
}
