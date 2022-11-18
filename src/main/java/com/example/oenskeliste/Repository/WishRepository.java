package com.example.oenskeliste.Repository;
import com.example.oenskeliste.Model.DCM;
import com.example.oenskeliste.Model.Wish;
import com.example.oenskeliste.Service.UserService;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.WebRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class WishRepository {
    Connection connection = DCM.getConn();


    public String getPassword(String name){
        String QUARY = "SELECT Name, Password from user";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUARY);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String username = resultSet.getString(1);
                String password = resultSet.getString(2);

                if (name.equals(username)){
                    return password;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addWish(Wish wish, String name){

        String QUARY = "INSERT INTO wish (UserId,Name, Price, Link, Note ) VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUARY);

            preparedStatement.setInt(1, getUserId(name));
            preparedStatement.setString(2, wish.getName());
            preparedStatement.setString(3, wish.getPrice());
            preparedStatement.setString(4, wish.getLink());
            preparedStatement.setString(5, wish.getNote());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteWish(Wish wish){

        String QUARY = "DELETE from wish where Name = ?  and Price = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUARY);

            preparedStatement.setString(1,wish.getName());
            preparedStatement.setString(2,wish.getPrice());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAllWishes(String name){
        String QUARY = "DELETE from wish where UserId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUARY);

            preparedStatement.setInt(1,getUserId(name));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserId(String username) {
        //Finds user id
        String QUARY = "SELECT Id, Name from user";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUARY);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);

                if (name.equals(username)) {
                    System.out.println("ID: " + id +
                            "\nName: " + name);
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("Didnt work");
        return 0;
    }

}
