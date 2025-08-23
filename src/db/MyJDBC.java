package db;

import constants.CommonConstants;

import java.sql.*;

//JDBC - Java Database Connectivity
//this class will be our gateway in accessing our MySQL database
public class MyJDBC {
    //register new user to the database
    //true - register succes
    //false - register failure

    public  static boolean register(String username, String password) {
        //first check if the username already exists in the database
        try {
            if(!checkUser(username)){
                Connection connection = DriverManager.getConnection(CommonConstants.DB_URL,
                        CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

                //create insert query
                PreparedStatement insertUser = connection.prepareStatement(
                        "INSERT INTO " + CommonConstants.DB_USER_TABLE_NAME +
                                "(username, password)" + "VALUES(?, ?)"
                );

                //insert parameters in the insert query
                insertUser.setString(1, username);
                insertUser.setString(2, password);

                //update db with new user
                insertUser.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  false;
    }

    //check if username already exists in the database
    //false - user doesn't exists
    //true - user exists in the database
    public  static boolean checkUser(String username) {
        try{
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL,
                    CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
            PreparedStatement checkUserExists = connection.prepareStatement(
                    "SELECT * FROM " + CommonConstants.DB_USER_TABLE_NAME +
                            " WHERE USERNAME = ?"
            );
            checkUserExists.setString(1, username);

            ResultSet resultSet = checkUserExists.executeQuery();

            //check to see if the result set is empty, if it is empty it means that there was no data row

            if(!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean validateLogin(String username, String password) {
        try{
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL,
                    CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT * FROM " + CommonConstants.DB_USER_TABLE_NAME +
                            " WHERE USERNAME = ? AND PASSWORD = ?"
            );
            validateUser.setString(1, username);
            validateUser.setString(2, password);

            ResultSet resultSet = validateUser.executeQuery();

            if(!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
