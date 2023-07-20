package com.ekov.quickFriends.Usecase;

import com.ekov.quickFriends.DAO.Dao;
import com.ekov.quickFriends.DAO.RowsAndCols;
import com.ekov.quickFriends.Models.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class Usecase {

    private Dao dao;

    public Usecase(Dao dao) {
        this.dao = dao;
    }

    private void registration(String firstName, String lastName, String fatherName, String city, String school, String classNumber, String phoneNumber, String email, String password) throws SQLException {
        dao.registration(firstName, lastName, fatherName, city, school, classNumber, phoneNumber, email, password);
    }

    private boolean login(String email, String password) throws SQLException {
        return dao.login(email, password);
    }


    private RowsAndCols query() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ddbb?serverTimezone=Europe/Moscow&useSSL=false", "root", "bestuser");
        Statement statement = conn.createStatement();
        List<List<String>> rows = new ArrayList<List<String>>();
        List<String> cols = new ArrayList<String>();

        ResultSet result = statement.executeQuery("SELECT Number, firstName, lastName, fatherName, City, School, Class, Email, Phone FROM ddbb.users");

        ResultSetMetaData rsmd = result.getMetaData();
        int columnCount = rsmd.getColumnCount();
        rows = dao.getRows(result, columnCount);
        cols = dao.getCols(rsmd, columnCount);
        return new RowsAndCols(rows, cols);
    }

    private User profileRider(Integer otherNumber) throws SQLException {
        return dao.profileRider(otherNumber);
    }

    private void updateAbout(String bout, String email) throws SQLException {
        dao.updateAbout(bout, email);
    }

    private void searchInTable(String str) {

    }




    public boolean getLogin(String email, String password) throws SQLException {
        return login(email, password);
    }

    public void getRegistration(String firstName, String lastName, String fatherName, String city, String school, String classNumber, String phoneNumber, String email, String password) throws SQLException {
        registration(firstName, lastName, fatherName, city, school, classNumber, phoneNumber, email, password);
    }

    public RowsAndCols getQuery() throws SQLException {
        return query();
    }

    public User getProfileRider(Integer otherNumber) throws SQLException {
        return profileRider(otherNumber);
    }

    public void getUpdateAbout(String bout, String email) throws SQLException {
        dao.updateAbout(bout, email);
    }

}
