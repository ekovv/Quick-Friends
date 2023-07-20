package com.ekov.quickFriends.DAO;

import com.ekov.quickFriends.Models.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.*;

@Repository
public class Dao {

    public void registration(String firstName, String lastName, String fatherName, String city, String school, String classNumber, String phoneNumber, String email, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ddbb?serverTimezone=Europe/Moscow&useSSL=false", "root", "bestuser");
        PreparedStatement statement = conn.prepareStatement("INSERT ddbb.users(firstName, lastName, fatherName, City, School, Class, Email, Phone, password) VALUES (?,?,?,?,?,?,?,?,?)");
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, fatherName);
        statement.setString(4, city);
        statement.setString(5, school);
        statement.setString(6, classNumber);
        statement.setString(7, phoneNumber);
        statement.setString(8, email);
        statement.setString(9, password);
        statement.executeUpdate();

    }

    public boolean login(String email, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ddbb?serverTimezone=Europe/Moscow&useSSL=false", "root", "bestuser");
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM ddbb.users WHERE email =? AND password =?");
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;
        if (resultSet.next()) {
            count += 1;
        }
        if (count == 1) {
            System.out.println("Уже зареган");
            return true;
        }
        return false;
    }



    public List<List<String>> getRows(ResultSet result, int ColumnCount) throws SQLException {
        List<String[]> rows = new ArrayList<>();
        while (result.next()) {
            String[] row = new String[ColumnCount];
            for (int i = 1; i <= ColumnCount; i++) {
                row[i - 1] = result.getString(i);
            }
            rows.add(row);
        }
        String[][] array = new String[rows.size()][ColumnCount];
        for (int i = 0; i < rows.size(); i++) {
            array[i] = rows.get(i);
        }
        List<List<String>> valueNames = new ArrayList<List<String>>();
        for (int i = 0; i < array.length; i++) {
            valueNames.add(List.of(array[i]));
        }
        return valueNames;
    }

    //getting table columns
    public List<String> getCols(ResultSetMetaData rsmd, int ColumnCount) throws SQLException {
        List<String> columnNames = new ArrayList<String>();
        for (int i = 1; i < ColumnCount + 1; i++) {
            columnNames.add(rsmd.getColumnName(i));
        }
        return columnNames;
    }


    public User profileRider(Integer otherNumber) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ddbb?serverTimezone=Europe/Moscow&useSSL=false", "root", "bestuser");
        PreparedStatement statement = conn.prepareStatement("SELECT FirstName, LastName, FatherName, City, School, Class, Email, Phone FROM ddbb.users WHERE Number = ?");
        statement.setInt(1, otherNumber);
        ResultSet resultSet = statement.executeQuery();
        User user = new User();
        resultSet.next();
        user.FirstName = resultSet.getString("FirstName");
        user.LastName = resultSet.getString("LastName");
        user.FatherName = resultSet.getString("FatherName");
        user.City = resultSet.getString("City");
        user.School = resultSet.getString("School");
        user.ClassNumber = resultSet.getString("Class");
        user.Email = resultSet.getString("Email");
        user.Phone = resultSet.getString("Phone");
        return user;
    }


    public void updateAbout(String bout, String email) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ddbb?serverTimezone=Europe/Moscow&useSSL=false", "root", "bestuser");
        PreparedStatement statement = conn.prepareStatement("UPDATE ddbb.users SET about = ? WHERE Email = ?");
        statement.setString(1, bout);
        statement.setString(2, email);
        statement.executeUpdate();

    }



}
