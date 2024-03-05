package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Restaurant;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Détails {

    private int likesCount = 0;
    private int dislikesCount = 0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button likeButton;

    @FXML
    private Button dislikeButton;

    @FXML
    private URL location;


    @FXML
    private Label productNameLabel;

    @FXML
    private Button Réserver;

    @FXML
    private Label descriptionproduitlabel;

    @FXML
    private Label prixproduitlabel;

    @FXML
    private ImageView productImageView;





    @FXML
    private Label likeCountLabel;

    @FXML
    private Label DislikeButtonLabel;


    private Restaurant selectedRestaurant;

    public void initData(Restaurant restaurant) {
        this.selectedRestaurant = restaurant;
        if (restaurant != null) {
            productNameLabel.setText(restaurant.getNom());
            String imagePath = restaurant.getImg();
            Image image = new Image(new File(imagePath).toURI().toString());
            productImageView.setImage(image);
            descriptionproduitlabel.setText(restaurant.getType());
            prixproduitlabel.setText(restaurant.getAdresse());
        }
    }

    @FXML
    private void handleRéserver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation.fxml"));
            Parent root = loader.load();

            Reservation reservationController = loader.getController();
            reservationController.initData(selectedRestaurant);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void likeAction(ActionEvent event) {
        likesCount++;
        likeCountLabel.setText(String.valueOf(likesCount));

        Notifications.create()
                .title("Notification")
                .text("Vous avez aimé ce produit !")
                .showInformation();
    }

    @FXML
    void dislikeAction(ActionEvent event) {
        dislikesCount++;
        DislikeButtonLabel.setText(String.valueOf(dislikesCount));

        Notifications.create()
                .title("Notification")
                .text("Vous n'avez pas aimé ce produit !")
                .showInformation();    }


}
