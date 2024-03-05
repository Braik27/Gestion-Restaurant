package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Restaurant;

import java.io.File;


public class Item {
    @FXML
    private Label nameLabel;


    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent event) {
        if (myListener != null) {
            myListener.onClickListener(restaurant);
        }
    }


    private Restaurant restaurant;
    private Listener myListener;

    public void setData(Restaurant restaurant, Listener myListener) {
        this.restaurant = restaurant;
        this.myListener = myListener;
        nameLabel.setText(restaurant.getNom());


        // Assuming produit.getImage() returns the path string
        String imagePath = restaurant.getImg();
        try {
            // Assuming imagePath is a valid path to the image resource
            Image image = new Image(new File(imagePath).toURI().toString());
            img.setImage(image);
        } catch (Exception e) {
            // Handle the exception (e.g., log it or display an error message)
            e.printStackTrace();
        }
    }


}
