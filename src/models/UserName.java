package models;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserName {

    private String name;
    private String password;

    public UserName(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validateUserName(String newUser) {
        //TODO add validation, if staff or ...
        return false;
    }

    public static boolean validatePassword(String newPassword) {
        char[] password = newPassword.toCharArray();
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasDigit = false;

        for (char c : password) {
            if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasUpper = true;
            else if (Character.isUpperCase(c)) hasDigit = true;
        }
        return hasUpper && hasLower && hasDigit && newPassword.length() > 7;
    }

    public static boolean validateEmail(String email) {
        return email.contains("@") && email.contains(".com") && email.length() > 10;
    }

    public static boolean validateName(String name) {
        return Character.isUpperCase(name.charAt(0));
    }

    public static String correctName(String name) {
        char[] nameArray = name.toCharArray();
        if (!Character.isUpperCase(nameArray[0])) {
            nameArray[0] = Character.toUpperCase(nameArray[0]);
        }
        return Arrays.toString(nameArray);
    }

}
