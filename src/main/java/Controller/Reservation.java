package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Restaurant;
import org.controlsfx.control.Notifications;

public class Reservation {
    @FXML
    private TextField nomuser;

    @FXML
    private TextField emailuser;

    @FXML
    private TextField teluser;

    @FXML
    private TextField date;

    @FXML
    private TextField heure;

    @FXML
    private TextField nbrpersonne;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgrest;

    public void initData(Restaurant restaurant) {
        if (restaurant != null) {
            String imagePath = restaurant.getImg();
            Image image = new Image(new File(imagePath).toURI().toString());
            imgrest.setImage(image);
        }
    }

    private boolean validateFields() {
        if (!validateTextField(nomuser, "Nom")
                || !validateTextField(emailuser, "Email")
                || !validateTextField(teluser, "Téléphone")
                || !validateTextField(date, "Date")
                || !validateTextField(heure, "Heure")
                || !validateTextField(nbrpersonne, "Nombre de personnes")) {
            return false;
        }

        if (!isValidEmail(emailuser.getText())) {
            showAlert("Veuillez saisir une adresse e-mail valide.");
            return false;
        }

        return true;
    }

    private boolean validateTextField(TextField textField, String fieldName) {
        if (textField.getText().isEmpty()) {
            showAlert("Le champ " + fieldName + " est obligatoire.");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,63}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champ obligatoire manquant");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean insertReservationIntoDatabase() {
        String nom = nomuser.getText();
        String email = emailuser.getText();
        String telephone = teluser.getText();
        String dateReservation = date.getText();
        String heureReservation = heure.getText();
        String nombrePersonnesText = nbrpersonne.getText().trim(); // Supprimer les espaces en début et fin de la chaîne

        // Vérifier si la chaîne est vide après la suppression des espaces
        if (nombrePersonnesText.isEmpty()) {
            showAlert("Le champ Nombre de personnes est obligatoire.");
            return false;
        }

        // Convertir la chaîne en un entier
        int nombrePersonnes;
        try {
            nombrePersonnes = Integer.parseInt(nombrePersonnesText);
        } catch (NumberFormatException e) {
            showAlert("Veuillez saisir un nombre valide pour le champ Nombre de personnes.");
            return false;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tounsifit", "root", "")) {
            String sql = "INSERT INTO reservation (nom, email, telephone, date_reservation, heure_reservation, nombre_personnes) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nom);
            statement.setString(2, email);
            statement.setString(3, telephone);
            statement.setString(4, dateReservation);
            statement.setString(5, heureReservation);
            statement.setInt(6, nombrePersonnes);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Données insérées avec succès.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion des données : " + e.getMessage());
        }
        return false;
    }


    @FXML
    void confirmerAction(ActionEvent event) {
        if (validateFields()) {
            if (insertReservationIntoDatabase()) {
                ouvrirTableDesReservations();
            }
        }
    }

    private void ouvrirTableDesReservations() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/payment_form.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Notifications.create()
                    .title("Notification")
                    .text("Reservation confirmée.")
                    .showInformation();



    } catch(
    Exception e)

    {
        e.printStackTrace();
    }

}



    @FXML
    void initialize() {

    }
}
