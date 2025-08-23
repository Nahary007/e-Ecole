package db;

import constants.CommonConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyJDBC {

    public static boolean register(String username, String password) {
        try {
            if (!checkUser(username)) {
                try (Connection connection = DriverManager.getConnection(
                        CommonConstants.DB_URL,
                        CommonConstants.DB_USERNAME,
                        CommonConstants.DB_PASSWORD);
                     PreparedStatement insertUser = connection.prepareStatement(
                             "INSERT INTO " + CommonConstants.DB_USER_TABLE_NAME + " (username, password) VALUES (?, ?)")) {

                    insertUser.setString(1, username);
                    insertUser.setString(2, password);
                    insertUser.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkUser(String username) {
        try (Connection connection = DriverManager.getConnection(
                CommonConstants.DB_URL,
                CommonConstants.DB_USERNAME,
                CommonConstants.DB_PASSWORD);
             PreparedStatement checkUserExists = connection.prepareStatement(
                     "SELECT * FROM " + CommonConstants.DB_USER_TABLE_NAME + " WHERE username = ?")) {

            checkUserExists.setString(1, username);
            ResultSet resultSet = checkUserExists.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean validateLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(
                CommonConstants.DB_URL,
                CommonConstants.DB_USERNAME,
                CommonConstants.DB_PASSWORD);
             PreparedStatement validateUser = connection.prepareStatement(
                     "SELECT * FROM " + CommonConstants.DB_USER_TABLE_NAME + " WHERE username = ? AND password = ?")) {

            validateUser.setString(1, username);
            validateUser.setString(2, password);
            ResultSet resultSet = validateUser.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                CommonConstants.DB_URL,
                CommonConstants.DB_USERNAME,
                CommonConstants.DB_PASSWORD
        );
    }

    public static List<String[]> getAllStudents() {
        List<String[]> students = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, nom, prenom, email, filiere FROM students");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("filiere")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public static boolean deleteStudent(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE id = ?")) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
