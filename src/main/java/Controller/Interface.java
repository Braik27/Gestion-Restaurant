package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


import Services.RestaurantService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.Restaurant;

public class Interface implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Button addToCartButton;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private List<Restaurant> restaurants = new ArrayList<>();
    private Listener myListener;

    public List<Restaurant> getData() {
        RestaurantService restaurantService = new RestaurantService();
        try {
            // Récupérer la liste des restaurants depuis la base de données
            List<Restaurant> retrievedRestaurants = restaurantService.recuperer();
            // Si des restaurants ont été récupérés avec succès
            if (retrievedRestaurants != null) {
                // Afficher le nombre de restaurants récupérés
                System.out.println("Nombre de produits récupérés : " + retrievedRestaurants.size());
                // Affecter la liste des restaurants récupérés à votre liste restaurants
                restaurants.addAll(retrievedRestaurants);
            }
            // Retourner la liste des restaurants récupérés (peut être utile dans d'autres parties de votre application)
            return retrievedRestaurants;
        } catch (SQLException e) {
            // Gérer toute exception SQL
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la récupération des produits : " + e.getMessage());
            alert.show();
            return null;
        }
    }


    public Restaurant getRestaurantById(int id) {
        RestaurantService restaurantService = new RestaurantService();
        try {
            Restaurant restaurant = restaurantService.recupererRestaurantParId(id);
            if (restaurant != null) {
                System.out.println("Produit récupéré : " + restaurant.getNom());
            } else {
                System.out.println("Aucun produit trouvé avec l'ID : " + id);
            }
            return restaurant;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la récupération du produit : " + e.getMessage());
            alert.show();
            return null;
        }
    }

    private void setChosenFruit(Restaurant restaurant) {
        fruitNameLable.setText(restaurant.getNom());
        String imagePath = restaurant.getImg();

        Image image = new Image(new File(imagePath).toURI().toString());
        fruitImg.setImage(image);
    }

    @FXML
    private void handleRestaurantSelection(ActionEvent event) {
        int selectedRestaurantId = getRestaurantIdFromEvent(event);

        Restaurant selectedProduct = getRestaurantById(selectedRestaurantId);

        if (selectedProduct != null) {
            setChosenFruit(selectedProduct);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le produit n'a pas été trouvé.");
            alert.showAndWait();
        }
    }

    private int getRestaurantIdFromEvent(ActionEvent event) {

        Button button = (Button) event.getSource();
        return Integer.parseInt(button.getUserData().toString());


    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        restaurants.addAll(getData());
        if (!restaurants.isEmpty()) {
            setChosenFruit(restaurants.get(0));
            myListener = restaurant -> setChosenFruit(restaurant);
        }
        int column = 0;
        int row = 1;
        try {
            for (Restaurant restaurant : restaurants) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                Item item = fxmlLoader.getController();
                item.setData(restaurant, myListener);


                Button addToCartButton = new Button("Détails");
                addToCartButton.setOnAction(this::handleAjouterAuPanier);
                addToCartButton.setUserData(restaurant.getId());

                addToCartButton.setPrefWidth(270);
                addToCartButton.setPrefHeight(20);
                addToCartButton.getStyleClass().add("add-btn");
                addToCartButton.setFont(Font.font("System Bold", 18));
                addToCartButton.setTextFill(Color.web("#828282"));

                // Add the button to the AnchorPane
                anchorPane.getChildren().add(addToCartButton);
                AnchorPane.setTopAnchor(addToCartButton, 240.0);
                AnchorPane.setLeftAnchor(addToCartButton, 10.0);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void handleAjouterAuPanier(ActionEvent event) {
        Button button = (Button) event.getSource();
        int selectedRestaurantId = (int) button.getUserData();

        Restaurant selectedRestaurant = getRestaurantById(selectedRestaurantId);

        if (selectedRestaurant != null) {
            try {
                // Charger le fichier FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Détails.fxml"));
                Parent root = loader.load();

                // Initialiser le contrôleur de la nouvelle fenêtre et passer les données du restaurant sélectionné
                Détails detailsController = loader.getController();
                detailsController.initData(selectedRestaurant);

                // Créer une nouvelle fenêtre pour afficher les détails du restaurant
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le produit n'a pas été trouvé.");
            alert.showAndWait();
        }
    }


}
