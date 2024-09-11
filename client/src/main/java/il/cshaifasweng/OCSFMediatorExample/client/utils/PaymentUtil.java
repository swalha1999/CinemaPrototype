package il.cshaifasweng.OCSFMediatorExample.client.utils;

public class PaymentUtil {

    // Luhn algorithm for Visa
    public static boolean validateVisaCard(String cardNumber) {
        return cardNumber.startsWith("4") && luhnCheck(cardNumber);
    }

    // Specific logic for MasterCard
    public static boolean validateMasterCard(String cardNumber) {
        return cardNumber.startsWith("5") && luhnCheck(cardNumber);
    }

    // Specific logic for American Express
    public static boolean validateAmexCard(String cardNumber) {
        return (cardNumber.startsWith("34") || cardNumber.startsWith("37")) && luhnCheck(cardNumber);
    }

    // Function to check if the card is valid for any type (Visa, MasterCard, or Amex)
    public static boolean isValidCard(String cardNumber) {
        return validateVisaCard(cardNumber) || validateMasterCard(cardNumber) || validateAmexCard(cardNumber);
    }

    // Luhn algorithm to check if a card number is valid
    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
