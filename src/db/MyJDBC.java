package db;

import constants.CommonConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire pour gérer les opérations JDBC liées aux utilisateurs et étudiants.
 */
public class MyJDBC {
    /**
     * Vérifie si les identifiants de connexion sont valides.
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return true si l'utilisateur existe, false sinon
     */
    public static boolean validateLogin(String username, String password) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            // Prépare la requête SQL pour vérifier l'utilisateur
            String sql = "SELECT * FROM " + CommonConstants.DB_USER_TABLE_NAME + " WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            // Retourne true si un résultat est trouvé
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Enregistre un nouvel utilisateur dans la base de données.
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return true si l'insertion a réussi, false sinon
     */
    public static boolean register(String username, String password) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            // Prépare la requête SQL pour insérer un nouvel utilisateur
            String sql = "INSERT INTO " + CommonConstants.DB_USER_TABLE_NAME + " (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();
            // Retourne true si au moins une ligne a été insérée
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère tous les étudiants de la base de données.
     * @return liste des étudiants (tableaux de chaînes)
     */
    public static List<String[]> getAllStudents() {
        List<String[]> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            // Requête SQL pour sélectionner tous les étudiants
            String sql = "SELECT id, nom, prenom, email, filiere FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // Ajoute chaque étudiant à la liste
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

    /**
     * Supprime un étudiant par son identifiant.
     * @param id identifiant de l'étudiant
     * @return true si la suppression a réussi, false sinon
     */
    public static boolean deleteStudent(int id) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            // Requête SQL pour supprimer l'étudiant
            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            // Retourne true si au moins une ligne a été supprimée
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère les informations d'un étudiant par son identifiant.
     * @param id identifiant de l'étudiant
     * @return tableau contenant les informations de l'étudiant, ou null si non trouvé
     */
    public static String[] getStudentById(int id) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            // Requête SQL pour sélectionner l'étudiant par ID
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

    /**
     * Met à jour les informations d'un étudiant.
     * @param id identifiant de l'étudiant
     * @param nom nouveau nom
     * @param prenom nouveau prénom
     * @param email nouvel email
     * @param filiere nouvelle filière
     * @return true si la mise à jour a réussi, false sinon
     */
    public static boolean updateStudent(int id, String nom, String prenom, String email, String filiere) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            // Requête SQL pour mettre à jour l'étudiant
            String sql = "UPDATE students SET nom = ?, prenom = ?, email = ?, filiere = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setString(4, filiere);
            stmt.setInt(5, id);
            int rowsAffected = stmt.executeUpdate();
            // Retourne true si au moins une ligne a été modifiée
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ajoute un nouvel étudiant à la base de données.
     * @param nom nom de l'étudiant
     * @param prenom prénom de l'étudiant
     * @param email email de l'étudiant
     * @param filiere filière de l'étudiant
     * @return true si l'ajout a réussi, false sinon
     */
    public static boolean addStudent(String nom, String prenom, String email, String filiere) {
        try (Connection conn = DriverManager.getConnection(CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD)) {
            // Requête SQL pour insérer un nouvel étudiant
            String sql = "INSERT INTO students (nom, prenom, email, filiere) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setString(4, filiere);
            int rowsAffected = stmt.executeUpdate();
            // Retourne true si au moins une ligne a été insérée
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}