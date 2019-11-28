package models;

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

    public boolean validatePassword(String newPassword) {
        boolean check = true;
        if (newPassword.length() < 8) check = false;
        else if (newPassword.matches(this.name)) check = false;
        //Todo add more criteria to make password strong
            return check;
    }

}
