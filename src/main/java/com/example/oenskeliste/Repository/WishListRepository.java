package com.example.oenskeliste.Repository;

import com.example.oenskeliste.Model.DCM;
import com.example.oenskeliste.Model.Wish;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class WishListRepository {

    Connection connection = DCM.getConn();

    public ArrayList<Wish> getWishesByPassword(String password) {

        //Finds the user by password
        String PASSWORD_QUERY = "SELECT Id, Password from user";
        int userId = 0;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PASSWORD_QUERY);

            while (resultSet.next()) {

                String pw = resultSet.getString(2);

                if (pw.equals(password)) {
                    userId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Selects wishes by user id

        ArrayList<Wish> wishes = new ArrayList<>();

        String MAIN_QUERY = "SELECT * FROM wish WHERE UserId = " + userId;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MAIN_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Wish wish = new Wish(resultSet.getString(2),
                                     resultSet.getString(3),
                                     resultSet.getString(4),
                                     resultSet.getString(5));

                wishes.add(wish);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wishes;
    }


    public String getNameFromPassword(String password) {
        String PASSWORD_QUERY = "SELECT Name, Password FROM user";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(PASSWORD_QUERY);

            while (resultSet.next()) {
                String pw = resultSet.getString(2);

                if (pw.equals(password)) {
                    String name = resultSet.getString(1);
                    System.out.println("Name is:" + name);

                    return name;
                }
            }
        } catch (SQLException e) {
            System.out.println("Did not find name");
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean checkIfListExists(String password){

        String QUARY = "SELECT * from user";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUARY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String userPassword = resultSet.getString(4);

                if (userPassword.equals(password)){
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}