package Controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import Services.RestaurantService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Restaurant;

public class RestaurantInfo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Restaurant, String> columnAdresse;

    @FXML
    private TableColumn<Restaurant, String> columnImage;

    @FXML
    private TableColumn<Restaurant, String> columnNom;

    @FXML
    private TableColumn<Restaurant, String> columnType;

    @FXML
    private TableView<Restaurant> tableViewProduits;

        @FXML
        void initialize() {
            List<Restaurant> restaurants = getRestaurants();
            initializeRestaurants(restaurants);
            columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            columnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
            columnAdresse.setCellValueFactory(new PropertyValueFactory<>("Adresse"));
            columnImage.setCellValueFactory(new PropertyValueFactory<>("img"));

            columnImage.setCellFactory(column -> {
                return new javafx.scene.control.TableCell<Restaurant, String>() {
                    private final ImageView imageView = new ImageView();


                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            try {
                                URL imageUrl = new File(item).toURI().toURL();
                                Image image = new Image(imageUrl.toString());
                                imageView.setImage(image);
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);
                                setGraphic(imageView);
                            } catch (MalformedURLException e) {
                                System.err.println("Error loading image: " + e.getMessage());
                                setGraphic(null);
                            }
                        }
                    }
                };
            });
        }
    public void initializeRestaurants(List<Restaurant> restaurants) {
        if (restaurants != null) {
            ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList(restaurants);
            tableViewProduits.setItems(restaurantList);
        }
    }
    public List<Restaurant> getRestaurants() {
        RestaurantService restaurantService = new RestaurantService();
        try {
            List<Restaurant> restaurants = restaurantService.recuperer();
            System.out.println("nombre de produits "+ restaurants.size());
            return restaurants;
        } catch (SQLException e) {
            // Handle any SQL exceptions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de la récupération des produits : " + e.getMessage());
            alert.show();
            return null;
        }
    }
}




