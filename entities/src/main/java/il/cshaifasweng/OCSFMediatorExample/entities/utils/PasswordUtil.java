package il.cshaifasweng.OCSFMediatorExample.entities.utils;

public class PasswordUtil {
    static public int passwordStrength(String password) {
        int strength = 0;
        if (password.length() >= 8) {
            strength++;
        }
        if (password.matches(".*[a-z].*")) {
            strength++;
        }
        if (password.matches(".*[A-Z].*")) {
            strength++;
        }
        if (password.matches(".*[0-9].*")) {
            strength++;
        }
        if (password.matches(".*[!@#$%^&*()].*")) {
            strength++;
        }
        return strength;
    }

    static public boolean isStrong(String password) {
        return passwordStrength(password) >= 3;
    }

    static public boolean isWeak(String password) {
        return passwordStrength(password) < 3;
    }

    static public boolean isVeryWeak(String password) {
        return passwordStrength(password) < 2;
    }

    static public boolean isVeryStrong(String password) {
        return passwordStrength(password) == 5;
    }


}
