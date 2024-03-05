package Services;

import Utils.DataBase;
import models.Restaurant;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantService implements IService<Restaurant> {
    private Connection connection;
    public RestaurantService (){

        connection = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Restaurant restaurant) throws SQLException {
        String sql = "insert into restaurant (nom, adresse,type,img) " +
                "values('" + restaurant.getNom() + "','" + restaurant.getAdresse() + "'"
                +  ",'" + restaurant.getType() + "','" + restaurant.getImg()+"')";
        Statement statement = DataBase.getInstance().getConnection().createStatement();
        statement.executeUpdate(sql);



    }


    @Override
    public void modifier(Restaurant restaurant) throws SQLException {
        String sql = "update restaurant set nom = ?, adresse = ?, type = ?, img = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, restaurant.getNom());
        preparedStatement.setString(2, restaurant.getAdresse());
        preparedStatement.setString(3, restaurant.getType());
        preparedStatement.setString(4, restaurant.getImg());
        preparedStatement.setInt(5, restaurant.getId());
        preparedStatement.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `restaurant` WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Restaurant> recuperer() throws SQLException {
        String sql = "select * from restaurant";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<Restaurant> list = new ArrayList<>();
        while (rs.next()) {
            Restaurant r = new Restaurant();
            r.setId(rs.getInt("id"));
            r.setAdresse(rs.getString("adresse"));
            r.setNom(rs.getString("nom"));
            r.setType(rs.getString("type"));
            r.setImg(rs.getString("img"));
            list.add(r);

        }
        return list;
    }

    @Override
    public Restaurant recupererRestaurantParId(int id) throws SQLException {
        String sql = "SELECT * FROM restaurant WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        Restaurant restaurant = null;
        if (rs.next()) {
            restaurant = new Restaurant();
            restaurant.setId(rs.getInt("id"));
            restaurant.setNom(rs.getString("nom"));
            restaurant.setAdresse(rs.getString("adresse"));
            restaurant.setType(rs.getString("type"));
            restaurant.setImg(rs.getString("img"));
        }
        return restaurant;
    }



}

