package db;

import constants.CommonConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyJDBC {
    // Vérifier les identifiants de connexion
    public static boolean validateLogin(String username, String password) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            String sql = "SELECT * FROM " + CommonConstants.DB_USER_TABLE_NAME + " WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Enregistrer un nouvel utilisateur
    public static boolean register(String username, String password) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            String sql = "INSERT INTO " + CommonConstants.DB_USER_TABLE_NAME + " (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer tous les étudiants
    public static List<String[]> getAllStudents() {
        List<String[]> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            String sql = "SELECT id, nom, prenom, email, filiere FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                students.add(new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("filiere")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Supprimer un étudiant
    public static boolean deleteStudent(int id) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer un étudiant par ID
    public static String[] getStudentById(int id) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            String sql = "SELECT nom, prenom, email, filiere FROM students WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new String[]{
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("filiere")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Mettre à jour un étudiant
    public static boolean updateStudent(int id, String nom, String prenom, String email, String filiere) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            String sql = "UPDATE students SET nom = ?, prenom = ?, email = ?, filiere = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setString(4, filiere);
            stmt.setInt(5, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ajouter un étudiant
    public static boolean addStudent(String nom, String prenom, String email, String filiere) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            String sql = "INSERT INTO students (nom, prenom, email, filiere) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setString(4, filiere);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}