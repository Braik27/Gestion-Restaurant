package Test;

import Services.RestaurantService;
import models.Restaurant;
import Services.RestaurantService;
import Utils.DataBase;
import models.Restaurant;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DataBase database = DataBase.getInstance();

        RestaurantService ps = new RestaurantService();
        try {


            ps.ajouter(new Restaurant(3, "Born", "lac2 ", "japonaise", "src/assets/files/gran.png"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {

            ps.modifier(new Restaurant(4, "gold", "manar ", "100% healthy", "src/assets/files/gran.png"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {

            System.out.println( ps.recuperer());
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        try {

            ps.supprimer(3);;
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
}
