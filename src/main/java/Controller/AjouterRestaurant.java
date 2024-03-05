package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import Services.RestaurantService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Restaurant;

public class AjouterRestaurant {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Adressetextfield;

    @FXML
    private TextField Typetextfield;

    @FXML
    private ImageView imagetextfield;

    @FXML
    private TextField nomtextfield;


    RestaurantService restaurantService = new RestaurantService();


    @FXML
    void Ajouter(ActionEvent event) {
        String imagePath = "assets/fles/born and gold.jpg";
        Restaurant restaurant = new Restaurant(
                nomtextfield.getText(), Typetextfield.getText(),Adressetextfield.getText(),imagePath);


        try {
            restaurantService.ajouter(restaurant);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le produit a été ajouté");

            alert.setOnCloseRequest(e -> afficherProduitInfo());
            alert.show();


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void afficherProduitInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RestaurantInfo.fxml"));
            Parent root = loader.load();
            RestaurantInfo restaurantInfo = loader.getController();
            List<Restaurant> restaurants = restaurantService.recuperer();
            restaurantInfo.initializeRestaurants(restaurants);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void initialize() {

    }

}
