package Controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class Paiement {
        public static void main(String[] args) {
            Stripe.apiKey ="sk_test_51OpgudEJpKf7W6fkxNSdufRmGjxH3HOBUXRc9B4W5UNiNIXPH68JDq7sLL0DbAUjI6Fk1N4IeY2atmRM9EnbP78b00BBZEm40u";
            try {
// Retrieve your account information
                Account account = Account.retrieve();
                System.out.println("Account ID: " + account.getId());
// Print other account information as needed
            } catch (StripeException e) {
                e.printStackTrace();
            }
        }
    }

