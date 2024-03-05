package Controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.stripe.Stripe;

import java.util.regex.Pattern;

public class PaymentFormController {

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expirationDateField;

    @FXML
    private TextField securityCodeField;

    private boolean validateCardNumber(String cardNumber) {
        // Validation du numéro de carte avec une expression régulière
        String cardNumberPattern = "\\d{16}";
        return Pattern.matches(cardNumberPattern, cardNumber);
    }

    private boolean validateExpirationDate(String expirationDate) {
        // Validation de la date d'expiration avec une expression régulière
        String expirationDatePattern = "(0[1-9]|1[0-2])/[0-9]{2}";
        return Pattern.matches(expirationDatePattern, expirationDate);
    }

    private boolean validateSecurityCode(String securityCode) {
        // Validation du code de sécurité avec une expression régulière
        String securityCodePattern = "\\d{3}";
        return Pattern.matches(securityCodePattern, securityCode);
    }

    @FXML
    void processPayment() {
        String cardNumber = cardNumberField.getText();
        String expirationDate = expirationDateField.getText();
        String securityCode = securityCodeField.getText();

        // Vérification des saisies utilisateur
        if (!validateCardNumber(cardNumber)) {
            showAlert("Invalid Card Number", "Please enter a valid 16-digit card number.");
            return;
        }

        if (!validateExpirationDate(expirationDate)) {
            showAlert("Invalid Expiration Date", "Please enter a valid expiration date in MM/YY format.");
            return;
        }

        if (!validateSecurityCode(securityCode)) {
            showAlert("Invalid Security Code", "Please enter a valid 3-digit security code.");
            return;
        }

        try {
            Stripe.apiKey = "sk_test_51OpgudEJpKf7W6fkxNSdufRmGjxH3HOBUXRc9B4W5UNiNIXPH68JDq7sLL0DbAUjI6Fk1N4IeY2atmRM9EnbP78b00BBZEm40u";

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Montant en cents (par exemple, 10,00 $)
                    .setCurrency("usd")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            System.out.println(" Votre Reservation a effectuéé avec sucées. PaymentIntent ID: " + intent.getId());

            // Envoi d'une notification SMS à l'utilisateur
            sendSMSNotification("Votre reservation a effectuée avec sucées. Merci!");

            // Affichage d'une notification à l'utilisateur
            showAlert("Reservation", "Votre reservation a effectuée avec sucées. Merci!");

        } catch (StripeException e) {
            System.out.println("Reservation failed. Error: " + e.getMessage());
        }
    }

    private void sendSMSNotification(String message) {
        String ACCOUNT_SID = "ACdd5e61831be43b8a8a0f7a636fd661e9";
        String AUTH_TOKEN = "3dbfc4ca8da735662ba37b0a5eb66ee7";
        String TWILIO_PHONE_NUMBER = "+1 862 267 0495";
        String USER_PHONE_NUMBER = "+21658044443";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message.creator(
                        new PhoneNumber(USER_PHONE_NUMBER),
                        new PhoneNumber(TWILIO_PHONE_NUMBER),
                        message)
                .create();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
