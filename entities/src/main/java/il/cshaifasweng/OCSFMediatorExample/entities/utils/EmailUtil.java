package il.cshaifasweng.OCSFMediatorExample.entities.utils;

public class EmailUtil {

    /**
     * Validates an email address using a regular expression pattern.
     *
     * @param email the email address to validate
     * @return true if the email address is valid, false otherwise
     */
    public static boolean isValid(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    /**
     * Validates an email address using a custom regular expression pattern.
     *
     * @param email the email address to validate
     * @param pattern the custom regular expression pattern to use for validation
     * @return true if the email address matches the custom pattern, false otherwise
     */
    public static boolean isValid(String email, String pattern) {
        return email.matches(pattern);
    }

}